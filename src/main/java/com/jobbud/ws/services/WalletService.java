package com.jobbud.ws.services;

import com.jobbud.ws.entities.PendingAmountEntity;
import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.entities.WalletHistoryEntity;
import com.jobbud.ws.enums.WalletActionType;
import com.jobbud.ws.exceptions.NotFoundException;
import com.jobbud.ws.repositories.PendingAmountRepository;
import com.jobbud.ws.repositories.WalletHistoryRepository;
import com.jobbud.ws.repositories.WalletRepository;
import com.jobbud.ws.requests.WalletHistoryRequest;
import com.jobbud.ws.responses.WalletHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {
    private WalletHistoryService walletHistoryService;
    private WalletRepository walletRepository;
    private PendingAmountRepository pendingAmountRepository;

    @Autowired
    public WalletService(WalletHistoryService walletHistoryService, WalletRepository walletRepository, PendingAmountRepository pendingAmountRepository) {
        this.walletHistoryService = walletHistoryService;
        this.walletRepository = walletRepository;
        this.pendingAmountRepository = pendingAmountRepository;
    }


    public WalletEntity getWalletByUserId(long userId) {
        return walletRepository.findByUserId(userId);
    }

    public WalletEntity getWalletById(long walletId) {
        return walletRepository.findById(walletId).orElseThrow(() -> new NotFoundException("Wallet not found"));
    }

    public WalletEntity addAmountForMicroWork(long walletOwnerId, float amount) {

        WalletEntity walletEntity = getWalletByUserId(walletOwnerId);
        walletEntity.setBalance(walletEntity.getBalance() + amount);
        String description = "Earned " + amount + " TL for micro work";
        walletHistoryService.addWalletHistory(new WalletHistoryRequest(walletEntity.getId(), description, amount, WalletActionType.INCOMING));
        return walletRepository.save(walletEntity);
    }

    public WalletEntity addAmountFromPendingToWallet(long walletOwnerId, PendingAmountEntity pendingAmount) {
        WalletEntity walletEntity = getWalletByUserId(walletOwnerId);
        walletEntity.setBalance(walletEntity.getBalance() + pendingAmount.getAmount());
        pendingAmountRepository.delete(pendingAmount);
        String description = "Your pending amount "+ pendingAmount.getAmount() +" TL transferred to your balance";
        walletHistoryService.addWalletHistory(new WalletHistoryRequest(walletEntity.getId(), description, pendingAmount.getAmount(), WalletActionType.INCOMING));
        return walletRepository.save(walletEntity);
    }

    public PendingAmountEntity getPendingAmountInstance(long jobId) {
        return pendingAmountRepository.findByJobId(jobId);
    }

    public WalletEntity decreaseAmountFromWallet(WalletEntity walllet, float amount) {
        walllet.setBalance(walllet.getBalance() - amount);
        String description = "You paid " + amount + " TL ";
        walletHistoryService.addWalletHistory(new WalletHistoryRequest(walllet.getId(), description, amount, WalletActionType.OUTGOING));

        return walletRepository.save(walllet);
    }

    public List<WalletHistoryResponse> getWalletHistory(long walletId) {
        return walletHistoryService.getWalletHistory(walletId);
    }


}

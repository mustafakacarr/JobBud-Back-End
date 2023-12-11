package com.jobbud.ws.services;

import com.jobbud.ws.entities.PendingAmountEntity;
import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.exceptions.NotFoundException;
import com.jobbud.ws.repositories.PendingAmountRepository;
import com.jobbud.ws.repositories.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private WalletRepository walletRepository;
    private PendingAmountRepository pendingAmountRepository;

    public WalletService(WalletRepository walletRepository, PendingAmountRepository pendingAmountRepository) {
        this.walletRepository = walletRepository;
        this.pendingAmountRepository = pendingAmountRepository;
    }


    public WalletEntity getWalletByUserId(long userId) {
        return walletRepository.findByUserId(userId);
    }

    public WalletEntity getWalletById(long walletId) {
        return walletRepository.findById(walletId).orElseThrow(() -> new NotFoundException("Wallet not found"));
    }


    public WalletEntity addAmountFromPendingToWallet(long walletOwnerId, long jobId) {
        PendingAmountEntity pendingAmountEntity = pendingAmountRepository.findByJobId(jobId);
        WalletEntity walletEntity = getWalletByUserId(walletOwnerId);
        walletEntity.setBalance(walletEntity.getBalance() + pendingAmountEntity.getAmount());

        pendingAmountRepository.delete(pendingAmountEntity);
        return walletRepository.save(walletEntity);
    }
}

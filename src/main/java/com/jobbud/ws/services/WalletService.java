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

    public WalletEntity addAmountForMicroWork(long walletOwnerId, float amount) {
        WalletEntity walletEntity = getWalletByUserId(walletOwnerId);
        walletEntity.setBalance(walletEntity.getBalance() + amount);
        return walletRepository.save(walletEntity);
    }

    public WalletEntity addAmountFromPendingToWallet(long walletOwnerId, PendingAmountEntity pendingAmount) {
        WalletEntity walletEntity = getWalletByUserId(walletOwnerId);
        walletEntity.setBalance(walletEntity.getBalance() + pendingAmount.getAmount());
        pendingAmountRepository.delete(pendingAmount);
        return walletRepository.save(walletEntity);
    }

    public PendingAmountEntity getPendingAmountInstance(long jobId) {
        return pendingAmountRepository.findByJobId(jobId);
    }

    public WalletEntity decreaseAmountFromWallet(WalletEntity walllet, float amount) {
        walllet.setBalance(walllet.getBalance() - amount);
        return walletRepository.save(walllet);
    }
}

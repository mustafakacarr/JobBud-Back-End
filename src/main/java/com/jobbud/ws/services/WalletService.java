package com.jobbud.ws.services;

import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.repositories.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public WalletEntity getWalletByUserId(long userId) {
        return walletRepository.findByUserId(userId);
    }

    public WalletEntity getWalletById(long walletId) {
        return walletRepository.findById(walletId).orElse(null);
    }
}

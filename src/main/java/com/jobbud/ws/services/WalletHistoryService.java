package com.jobbud.ws.services;

import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.entities.WalletHistoryEntity;
import com.jobbud.ws.repositories.WalletHistoryRepository;
import com.jobbud.ws.repositories.WalletRepository;
import com.jobbud.ws.requests.WalletHistoryRequest;
import com.jobbud.ws.responses.WalletHistoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletHistoryService {
    private WalletHistoryRepository walletHistoryRepository;
    private WalletRepository walletRepository;

    public WalletHistoryService(WalletHistoryRepository walletHistoryRepository,  WalletRepository walletRepository) {
        this.walletHistoryRepository = walletHistoryRepository;
        this.walletRepository = walletRepository;
    }

    public List<WalletHistoryResponse> getWalletHistory(long walletId) {
        return walletHistoryRepository.findByWalletId(walletId).stream().map((history) ->
                new WalletHistoryResponse(history)).collect(Collectors.toList());
    }

    public WalletHistoryResponse addWalletHistory(WalletHistoryRequest walletHistoryRequest) {
        WalletEntity walletEntity = walletRepository.findById(walletHistoryRequest.getWalletId()).orElse(null);
        WalletHistoryEntity walletHistoryEntity = new WalletHistoryEntity();
        walletHistoryEntity.setDescription(walletHistoryRequest.getDescription());
        walletHistoryEntity.setAmount(walletHistoryRequest.getAmount());
        walletHistoryEntity.setWallet(walletEntity);
        walletHistoryEntity.setActionType(walletHistoryRequest.getActionType());

        return new WalletHistoryResponse(walletHistoryRepository.save(walletHistoryEntity));
    }
}

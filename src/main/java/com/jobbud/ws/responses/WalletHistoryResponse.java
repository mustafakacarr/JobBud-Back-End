package com.jobbud.ws.responses;

import com.jobbud.ws.entities.WalletHistoryEntity;
import lombok.Data;

@Data
public class WalletHistoryResponse {
    long id;
    String description;
    String actionType;
    long date;
    float amount;

    public WalletHistoryResponse(WalletHistoryEntity walletHistoryEntity) {
        this.id = walletHistoryEntity.getId();
        this.description = walletHistoryEntity.getDescription();
        this.actionType = walletHistoryEntity.getActionType().toString();
        this.date = walletHistoryEntity.getDate();
        this.amount = walletHistoryEntity.getAmount();
    }
}

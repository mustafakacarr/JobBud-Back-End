package com.jobbud.ws.requests;

import com.jobbud.ws.enums.WalletActionType;
import lombok.Data;

@Data
public class WalletHistoryRequest {
    private long walletId;
    private String description;
    private float amount;
    private WalletActionType actionType;

    public WalletHistoryRequest(long walletId, String description, float amount, WalletActionType actionType) {
        this.walletId = walletId;
        this.description = description;
        this.amount = amount;
        this.actionType = actionType;
    }
}

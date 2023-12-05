package com.jobbud.ws.requests;

import lombok.Data;

@Data
public class PendingAmountRequest {
    private long userId;
    private float amount;
    private long jobId;

    public PendingAmountRequest(long userId, float amount, long jobId) {
        this.userId = userId;
        this.amount = amount;
        this.jobId = jobId;
    }
}

package com.jobbud.ws.requests;

import lombok.Data;

@Data
public class PendingAmountRequest {
private long walletId;
    private float amount;
    private long jobId;


}

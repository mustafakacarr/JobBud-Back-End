package com.jobbud.ws.requests;

import com.jobbud.ws.entities.MicroTransactionEntity;
import lombok.Data;

@Data
public class MicroTransactionCreateRequest {
    private String label;
    private String description;
    private int quota;
    private int budget;
    private String channelId;
    private long ownerId;


}

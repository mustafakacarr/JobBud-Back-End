package com.jobbud.ws.responses;

import com.jobbud.ws.entities.MicroTransactionEntity;
import lombok.Data;

@Data
public class MicroTransactionResponse {
private long id;
private String label;
private String description;
private float budget;
private int numberDone;
private int quota;
private String channelId;

public MicroTransactionResponse(MicroTransactionEntity microTransactionEntity) {
        this.id = microTransactionEntity.getId();
        this.label = microTransactionEntity.getLabel();
        this.description = microTransactionEntity.getDescription();
        this.budget = microTransactionEntity.getBudget();
        this.numberDone = microTransactionEntity.getNumberDone();
        this.quota = microTransactionEntity.getQuota();
        this.channelId = microTransactionEntity.getChannelId();
}

}

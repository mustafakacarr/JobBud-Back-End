package com.jobbud.ws.responses;

import com.jobbud.ws.entities.OfferEntity;
import com.jobbud.ws.enums.OfferStatus;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class OfferResponse {
    private long id;
    private float price;
    private String description;
    private long datetime;
    private OfferStatus status;

    private long offerOwnerId;
    private String offerOwnerUsername;
    private long jobId;
    private String jobLabel;

    public OfferResponse(OfferEntity offerEntity) {
        this.id = offerEntity.getId();
        this.price = offerEntity.getPrice();
        this.description = offerEntity.getDescription();
        this.datetime = offerEntity.getDatetime();
        this.status = offerEntity.getStatus();
        this.offerOwnerId = offerEntity.getOwner().getId();
        this.offerOwnerUsername = offerEntity.getOwner().getUsername();
        this.jobId = offerEntity.getJob().getId();
        this.jobLabel = offerEntity.getJob().getLabel();
    }

}

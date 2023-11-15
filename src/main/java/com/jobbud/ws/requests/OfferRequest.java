package com.jobbud.ws.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobbud.ws.enums.OfferStatus;
import lombok.Data;

@Data
public class OfferRequest {
    private float price;
    private String description;
    private long ownerId;
    private long jobId;
    @JsonIgnore
    private final OfferStatus status=OfferStatus.OFFERED;
}


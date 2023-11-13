package com.jobbud.ws.requests;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.enums.GeneralStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.Date;

@Data
public class OfferRequest {
    private float price;
    private String description;
    private long ownerId;
    private long jobId;
    private final GeneralStatus status=GeneralStatus.OFFERED;
}


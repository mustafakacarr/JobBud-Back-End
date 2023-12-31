package com.jobbud.ws.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobbud.ws.enums.JobStatus;
import com.jobbud.ws.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobRequest {
    private String label;
    private String description;
    private float budget;
    private long deadline;
    private long ownerId;
    @JsonIgnore
    private final JobStatus status=JobStatus.WAITING_OFFERS;
}

package com.jobbud.ws.responses;

import com.jobbud.ws.entities.WorkEntity;
import lombok.Data;

@Data
public class WorkWithOfferResponse {


    private long id;
    private long workerId;
    private long jobId;
    private String jobLabel;
    private String jobDescription;
    private float actualJobPrice;
    private float acceptedOfferPrice;
    private String workContent;
    private long completedDate;
    private String status;

    public WorkWithOfferResponse(WorkEntity workEntity, OfferResponse offerResponse){
        this.id=workEntity.getId();
        this.workerId=workEntity.getWorker().getId();
        this.jobId=workEntity.getJob().getId();
        this.jobLabel=workEntity.getJob().getLabel();
        this.jobDescription=workEntity.getJob().getDescription();
        this.actualJobPrice=workEntity.getJob().getBudget();
        this.acceptedOfferPrice=offerResponse.getPrice();
        this.workContent=workEntity.getWorkContent();
        this.completedDate=workEntity.getCompletedDate();
        this.status=workEntity.getStatus().toString();
    }
}

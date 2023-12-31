package com.jobbud.ws.responses;

import com.jobbud.ws.entities.WorkEntity;
import com.jobbud.ws.services.OfferService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data

public class WorkResponse {


    private long id;
    private long workerId;
    private long jobId;
    private String jobLabel;
    private String jobDescription;
    private float actualJobPrice;
    private String workContent;
    private long completedDate;
    private String status;

    public WorkResponse(WorkEntity workEntity){
        this.id=workEntity.getId();
        this.workerId=workEntity.getWorker().getId();
        this.jobId=workEntity.getJob().getId();
        this.jobLabel=workEntity.getJob().getLabel();
        this.jobDescription=workEntity.getJob().getDescription();
        this.actualJobPrice=workEntity.getJob().getBudget();
        this.workContent=workEntity.getWorkContent();
        this.completedDate=workEntity.getCompletedDate();
        this.status=workEntity.getStatus().toString();
    }
}

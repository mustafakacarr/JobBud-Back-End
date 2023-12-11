package com.jobbud.ws.responses;

import com.jobbud.ws.entities.WorkEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class WorkResponse {
    private long id;
    private long workerId;
    private long jobId;
    private String workContent;
    private long completedDate;
    private String status;

    public WorkResponse(WorkEntity workEntity){
        this.id=workEntity.getId();
        this.workerId=workEntity.getWorker().getId();
        this.jobId=workEntity.getJob().getId();
        this.workContent=workEntity.getWorkContent();
        this.completedDate=workEntity.getCompletedDate();
        this.status=workEntity.getStatus().toString();
    }
}

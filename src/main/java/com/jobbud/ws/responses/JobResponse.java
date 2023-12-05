package com.jobbud.ws.responses;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.enums.JobStatus;
import lombok.Data;

@Data
public class JobResponse {
    private long id;
    private String label;
    private String description;
    private float budget;
    private long deadline;
    private long createdAt;
    private long jobOwnerId;
    private String jobOwnerUsername;
    private JobStatus status;

    public JobResponse(JobEntity jobEntity) {
        this.id = jobEntity.getId();
        this.label = jobEntity.getLabel();
        this.description = jobEntity.getDescription();
        this.budget = jobEntity.getBudget();
        this.deadline = jobEntity.getDeadline();
        this.createdAt = jobEntity.getCreatedDate();
        this.jobOwnerId = jobEntity.getOwner().getId();
        this.jobOwnerUsername = jobEntity.getOwner().getUsername();
        this.status = jobEntity.getStatus();
    }
}

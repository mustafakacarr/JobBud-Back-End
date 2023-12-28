package com.jobbud.ws.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobbud.ws.enums.WorkStatus;
import lombok.Data;

import java.util.Date;

@Data
public class WorkCreateRequest {
    private long workerId;
    private long jobId;
    private String workContent;
    @JsonIgnore
    private long completedDate;
    @JsonIgnore
    private final WorkStatus status=WorkStatus.WAITING_FINISH;

    public WorkCreateRequest(long workerId, long jobId, String workContent, long completedDate) {
        this.workerId = workerId;
        this.jobId = jobId;
        this.workContent = workContent;
        this.completedDate = new Date().getTime();
    }
}

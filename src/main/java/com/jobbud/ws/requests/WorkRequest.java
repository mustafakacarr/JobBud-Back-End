package com.jobbud.ws.requests;

import com.jobbud.ws.enums.WorkStatus;
import lombok.Data;

@Data
public class WorkRequest {
    private long workerId;
    private long jobId;
    private String workContent;
    private long completedDate;
    private WorkStatus status;

}

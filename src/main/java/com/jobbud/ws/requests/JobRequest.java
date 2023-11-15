package com.jobbud.ws.requests;

import com.jobbud.ws.enums.JobStatus;
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
    private JobStatus status;
}

package com.jobbud.ws.requests;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.enums.GeneralStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class WorkRequest {
    private long workerId;
    private long jobId;
    private String workContent;
    private long completedDate;
    private GeneralStatus status;

}

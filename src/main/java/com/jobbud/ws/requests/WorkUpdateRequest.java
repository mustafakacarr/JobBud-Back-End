package com.jobbud.ws.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobbud.ws.enums.WorkStatus;
import lombok.Data;

@Data
public class WorkUpdateRequest {
    private String workContent;
    private long completedDate;
    @JsonIgnore
    private final WorkStatus status=WorkStatus.WAITING_APPROVE;

}

package com.jobbud.ws.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobbud.ws.enums.WorkStatus;
import lombok.Data;

import java.util.Date;

@Data
public class WorkUpdateRequest {
    private String workContent;
    @JsonIgnore
    private long completedDate=new Date().getTime();
    @JsonIgnore
    private final WorkStatus status=WorkStatus.WAITING_APPROVE;

}

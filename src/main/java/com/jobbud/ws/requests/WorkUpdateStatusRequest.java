package com.jobbud.ws.requests;

import com.jobbud.ws.enums.WorkStatus;
import lombok.Data;

@Data
public class WorkUpdateStatusRequest {
    private WorkStatus status;
}

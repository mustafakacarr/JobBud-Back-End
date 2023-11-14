package com.jobbud.ws.requests;

import com.jobbud.ws.enums.UpdateStatus;
import lombok.Data;

@Data
public class UpdateStatusRequest {
private UpdateStatus status;
}

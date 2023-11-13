package com.jobbud.ws.requests;

import com.jobbud.ws.enums.GeneralStatus;
import lombok.Data;

@Data
public class UpdateStatusRequest {
private GeneralStatus status;
}

package com.jobbud.ws.requests;

import com.jobbud.ws.enums.OfferStatus;
import lombok.Data;

@Data
public class UpdateOfferStatusRequest {
private OfferStatus status;
}

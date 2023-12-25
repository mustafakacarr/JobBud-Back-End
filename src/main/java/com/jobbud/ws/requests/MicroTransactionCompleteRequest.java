package com.jobbud.ws.requests;

import lombok.Data;

@Data
public class MicroTransactionCompleteRequest {
    private long microTransactionId;
    //It will come from google callback
    private String code;
    private long freelancerId;

}

package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.PendingAmountEntity;
import com.jobbud.ws.requests.PendingAmountRequest;
import com.jobbud.ws.services.PendingAmountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/pendingamounts")
@SecurityRequirement(name = "JobBud Auth with Jwt")
public class PendingAmountController {
        private PendingAmountService pendingAmountService;
       public PendingAmountController(PendingAmountService pendingAmountService) {
           this.pendingAmountService = pendingAmountService;
       }

       @PostMapping
       public PendingAmountEntity createPendingAmount(@RequestBody PendingAmountRequest pendingAmountRequest){
        return pendingAmountService.addPendingAmount(pendingAmountRequest);
       }
}

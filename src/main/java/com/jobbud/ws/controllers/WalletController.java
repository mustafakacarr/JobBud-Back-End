package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.services.WalletService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/wallets")
@SecurityRequirement(name = "JobBud Auth with Jwt")
public class WalletController {

    private WalletService walletService;
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public WalletEntity getWalletByUserId(@RequestParam long userId){
        return walletService.getWalletByUserId(userId);
    }

    @GetMapping("/{walletId}")
    public WalletEntity getWalletById(@PathVariable long walletId){
        return walletService.getWalletById(walletId);
    }



}

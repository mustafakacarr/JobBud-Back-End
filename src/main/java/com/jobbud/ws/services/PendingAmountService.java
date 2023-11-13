package com.jobbud.ws.services;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.entities.PendingAmountEntity;
import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.repositories.JobRepository;
import com.jobbud.ws.repositories.PendingAmountRepository;
import com.jobbud.ws.repositories.WalletRepository;
import com.jobbud.ws.requests.PendingAmountRequest;
import org.springframework.stereotype.Service;

@Service
public class PendingAmountService {
    private PendingAmountRepository pendingAmountRepository;
    private WalletRepository walletRepository;
    private JobRepository jobRepository;

    public PendingAmountService(PendingAmountRepository pendingAmountRepository, WalletRepository walletRepository, JobRepository jobRepository) {
        this.pendingAmountRepository = pendingAmountRepository;
        this.walletRepository = walletRepository;
        this.jobRepository = jobRepository;
    }

    public PendingAmountEntity addPendingAmount(PendingAmountRequest pendingAmountRequest){
        PendingAmountEntity pendingAmountEntity = new PendingAmountEntity();
        WalletEntity wallet=walletRepository.findById(pendingAmountRequest.getWalletId()).orElse(null);
        JobEntity job=jobRepository.findById(pendingAmountRequest.getJobId()).orElse(null);
        pendingAmountEntity.setAmount(pendingAmountRequest.getAmount());
        pendingAmountEntity.setJob(job);
        pendingAmountEntity.setWallet(wallet);
       return pendingAmountRepository.save(pendingAmountEntity);
    }



}

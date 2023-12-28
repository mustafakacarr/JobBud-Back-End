package com.jobbud.ws.services;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.entities.PendingAmountEntity;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.exceptions.NotFoundException;
import com.jobbud.ws.repositories.JobRepository;
import com.jobbud.ws.repositories.PendingAmountRepository;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.repositories.WalletRepository;
import com.jobbud.ws.requests.PendingAmountRequest;
import org.springframework.stereotype.Service;

@Service
public class PendingAmountService {
    private PendingAmountRepository pendingAmountRepository;

    private WalletService walletService;
    private JobRepository jobRepository;
    private UserRepository userRepository;

    public PendingAmountService(PendingAmountRepository pendingAmountRepository, WalletService walletService, JobRepository jobRepository, UserRepository userRepository) {
        this.pendingAmountRepository = pendingAmountRepository;
        this.walletService = walletService;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public PendingAmountEntity addPendingAmount(PendingAmountRequest pendingAmountRequest){
        PendingAmountEntity pendingAmountEntity = new PendingAmountEntity();
        WalletEntity wallet=walletService.getWalletByUserId(pendingAmountRequest.getUserId());
        JobEntity job=jobRepository.findById(pendingAmountRequest.getJobId()).orElseThrow(() -> new NotFoundException("Job not found"));
        pendingAmountEntity.setAmount(pendingAmountRequest.getAmount());
        pendingAmountEntity.setJob(job);
        pendingAmountEntity.setWallet(wallet);
       return pendingAmountRepository.save(pendingAmountEntity);
    }

}

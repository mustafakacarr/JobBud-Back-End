package com.jobbud.ws.services;

import com.jobbud.ws.entities.MicroTransactionEntity;
import com.jobbud.ws.entities.MicroWorkEntity;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.helper.YoutubeHelper;
import com.jobbud.ws.repositories.MicroTransactionRepository;
import com.jobbud.ws.repositories.MicroWorkRepository;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.requests.GetChannelIdRequest;
import com.jobbud.ws.requests.MicroTransactionCompleteRequest;
import com.jobbud.ws.requests.MicroTransactionCreateRequest;
import com.jobbud.ws.responses.ChannelIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class MicroTransactionService {
    private MicroTransactionRepository microTransactionRepository;
    private YoutubeHelper youtubeHelper;
    private UserRepository userRepository;
    private MicroWorkRepository microWorkRepository;
    private WalletService walletService;

    public MicroTransactionService(MicroTransactionRepository microTransactionRepository, YoutubeHelper youtubeHelper, UserRepository userRepository, MicroWorkRepository microWorkRepository, WalletService walletService) {
        this.microTransactionRepository = microTransactionRepository;
        this.youtubeHelper = youtubeHelper;
        this.userRepository = userRepository;
        this.microWorkRepository = microWorkRepository;
        this.walletService = walletService;
    }

    public ResponseEntity<String> completeMicroTransaction(MicroTransactionCompleteRequest microTransactionCompleteRequest) throws IOException, URISyntaxException, InterruptedException {
        MicroTransactionEntity microTransaction=microTransactionRepository.findById(microTransactionCompleteRequest.getMicroTransactionId()).orElse(null);
        if(microTransaction.getNumberDone()==microTransaction.getQuota()){
            return ResponseEntity.badRequest().body("this microtransaction quota is already completed");
        }
        if (!microWorkRepository.existsByCompletedById(microTransactionCompleteRequest.getFreelancerId())) {
            MicroTransactionEntity microTransactionEntity = microTransactionRepository.findById(microTransactionCompleteRequest.getMicroTransactionId()).orElse(null);
            String code = microTransactionCompleteRequest.getCode();
            String accessToken = youtubeHelper.getAccessTokenViaCode(code);
            if (youtubeHelper.isUserSubscribed(accessToken, microTransactionEntity.getChannelId())) {
                microTransactionEntity.setNumberDone(microTransactionEntity.getNumberDone() + 1);
                MicroWorkEntity microWork = new MicroWorkEntity();
                microWork.setCompletedBy(microTransactionEntity.getOwner());
                microWork.setMicroTransaction(microTransactionEntity);
                microWorkRepository.save(microWork);
                walletService.addAmountForMicroWork(microTransactionEntity.getOwner().getId(), microTransactionEntity.getBudget() / microTransactionEntity.getQuota());

                return ResponseEntity.ok("user subscribed and amount will be transferred to freelancer. Freelancer earned " + (microTransactionEntity.getBudget() / microTransactionEntity.getQuota()) + " TL");
            } else {
                return ResponseEntity.badRequest().body("user not subscribed");

            }
        } else return ResponseEntity.badRequest().body("this microtransaction is already completed");
    }

    public MicroTransactionEntity addMicroTransaction(MicroTransactionCreateRequest microTransactionCreateRequest) {
        WalletEntity customerWallet = walletService.getWalletByUserId(microTransactionCreateRequest.getOwnerId());
        if (customerWallet.getBalance() < microTransactionCreateRequest.getBudget()) {
            throw new IllegalArgumentException("not enough balance");
        } else {
            walletService.decreaseAmountFromWallet(customerWallet, microTransactionCreateRequest.getBudget());

            UserEntity ownerOfMicroTransaction = userRepository.findById(microTransactionCreateRequest.getOwnerId()).orElse(null);
            MicroTransactionEntity microTransaction = new MicroTransactionEntity();
            microTransaction.setLabel(microTransactionCreateRequest.getLabel());
            microTransaction.setDescription(microTransactionCreateRequest.getDescription());
            microTransaction.setBudget(microTransactionCreateRequest.getBudget());
            microTransaction.setChannelId(microTransactionCreateRequest.getChannelId());
            microTransaction.setQuota(microTransactionCreateRequest.getQuota());
            microTransaction.setOwner(ownerOfMicroTransaction);
            return microTransactionRepository.save(microTransaction);
        }

    }

    public ChannelIdResponse findChannelId(GetChannelIdRequest getChannelIdRequest) throws IOException, URISyntaxException, InterruptedException {
        String accessToken = youtubeHelper.getAccessTokenViaCode(getChannelIdRequest.getCode());
        String channelId = youtubeHelper.getChannelId(accessToken);
        return new ChannelIdResponse(channelId);
    }
}

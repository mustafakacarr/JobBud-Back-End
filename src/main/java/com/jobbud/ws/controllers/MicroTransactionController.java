package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.MicroTransactionEntity;
import com.jobbud.ws.helper.YoutubeHelper;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.requests.GetChannelIdRequest;
import com.jobbud.ws.requests.MicroTransactionCompleteRequest;
import com.jobbud.ws.requests.MicroTransactionCreateRequest;
import com.jobbud.ws.responses.ChannelIdResponse;
import com.jobbud.ws.responses.MicroTransactionResponse;
import com.jobbud.ws.services.MicroTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1.0/microtransactions")
public class MicroTransactionController {

    //forChannelId will be customer's channel id
    private YoutubeHelper youtubeHelper;
    private MicroTransactionService microTransactionService;

    private UserRepository userRepository;

    @Autowired
    public MicroTransactionController(YoutubeHelper youtubeHelper, MicroTransactionService microTransactionService, UserRepository userRepository) {
        this.youtubeHelper = youtubeHelper;
        this.microTransactionService = microTransactionService;
        this.userRepository = userRepository;
    }


    @GetMapping("/oauth2/youtube/clientUrl")
    public String getConsentUrl() {
        return youtubeHelper.getConsentScreenUrl();
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeMicroTransaction(@RequestBody MicroTransactionCompleteRequest microTransactionCompleteRequest) throws IOException, URISyntaxException, InterruptedException {
        return microTransactionService.completeMicroTransaction(microTransactionCompleteRequest);

    }

    @PostMapping("/create")
    public MicroTransactionEntity addMicroTransaction(@RequestBody MicroTransactionCreateRequest microTransactionCreateRequest) throws IOException, URISyntaxException {
        return microTransactionService.addMicroTransaction(microTransactionCreateRequest);
    }

    @PostMapping("/findChannelId")
    public ChannelIdResponse findChannelId(@RequestBody GetChannelIdRequest getChannelIdRequest) throws IOException, URISyntaxException, InterruptedException {
        return microTransactionService.findChannelId(getChannelIdRequest);

    }

    @GetMapping()
    public List<MicroTransactionResponse> getMicroTransactions(@RequestParam Optional<Long> ownerId) {
        return microTransactionService.getMicroTransactions(ownerId);
    }

    @GetMapping("/{microTransactionId}")
    public MicroTransactionResponse getMicroTransactionById(@PathVariable long microTransactionId) {
        return microTransactionService.getMicroTransactionById(microTransactionId);
    }

    @GetMapping("/{microTransactionId}/isCompleted")
    public ResponseEntity<Boolean> isJobCompletedByCurrentUser(@PathVariable Long microTransactionId, @RequestParam Long freelancerId) {
        boolean isCompletedByCurrentUser = microTransactionService.isJobCompletedByCurrentUser(microTransactionId, freelancerId);
        return ResponseEntity.ok(isCompletedByCurrentUser);
    }

}
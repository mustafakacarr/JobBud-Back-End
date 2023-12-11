package com.jobbud.ws.controllers;


import com.jobbud.ws.requests.OfferRequest;
import com.jobbud.ws.requests.UpdateOfferStatusRequest;
import com.jobbud.ws.responses.OfferResponse;
import com.jobbud.ws.services.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1.0/offers")
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public OfferResponse createOffer(OfferRequest offerRequest) {
        return offerService.addOffer(offerRequest);

    }

    @GetMapping
    public List<OfferResponse> getOffers(@RequestParam Optional<Long> ownerId, @RequestParam Optional<Long> jobId) {
        return offerService.getOffers(ownerId, jobId);
    }

    // id
    @GetMapping("/{offerId}")
    public OfferResponse getOfferById(@PathVariable Long offerId) {
        return offerService.getOfferById(offerId);
    }

    @PutMapping("/{offerId}")
    public OfferResponse updateOfferStatus(@PathVariable long offerId, @RequestBody OfferRequest offerRequest) {
        return offerService.updateOffer(offerId, offerRequest);
    }

    // UPDATING STATUS OF OFFER (ACCEPTED OR REJECTED)
    @PutMapping("/{offerId}/status")
    public OfferResponse updateOfferStatus(@PathVariable long offerId, @RequestBody UpdateOfferStatusRequest updateOfferStatusRequest) {
        return offerService.updateOfferStatus(offerId, updateOfferStatusRequest);
    }

    // responseEntity is used to return http status codes
    //It's an instance of soft deletion, won't delete it permanently
    @DeleteMapping("/{offerId}")
    public ResponseEntity<HttpStatus> deleteOffer(@PathVariable long offerId) {
        try {
            offerService.deleteOffer(offerId);
            return new ResponseEntity("Offer successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.jobbud.ws.services;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.entities.OfferEntity;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.enums.JobStatus;
import com.jobbud.ws.enums.OfferStatus;
import com.jobbud.ws.exceptions.NotFoundException;
import com.jobbud.ws.repositories.JobRepository;
import com.jobbud.ws.repositories.OfferRepository;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.requests.OfferRequest;
import com.jobbud.ws.requests.PendingAmountRequest;
import com.jobbud.ws.requests.UpdateOfferStatusRequest;
import com.jobbud.ws.requests.WorkCreateRequest;
import com.jobbud.ws.responses.OfferResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {
    private OfferRepository offerRepository;
    private UserRepository userRepository;
    private JobRepository jobRepository;
    private WorkService workService;

    private PendingAmountService pendingAmountService;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository, JobRepository jobRepository, WorkService workService, PendingAmountService pendingAmountService) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.workService = workService;
        this.pendingAmountService = pendingAmountService;
    }

    public OfferResponse addOffer(OfferRequest offerRequest) {
        OfferEntity offer = new OfferEntity();
        UserEntity owner = userRepository.findById(offerRequest.getOwnerId()).orElseThrow(() -> new NotFoundException("User not found"));
        JobEntity job = jobRepository.findById(offerRequest.getJobId()).orElseThrow(() -> new NotFoundException("Job not found"));

        if (job.getStatus() == JobStatus.WAITING_OFFERS) {
            offer.setDescription(offerRequest.getDescription());
            offer.setStatus(offerRequest.getStatus());
            offer.setPrice(offerRequest.getPrice());
            offer.setOwner(owner);
            offer.setJob(job);
            return new OfferResponse(offerRepository.save(offer));
        }
        return null;
    }

    public OfferResponse getOfferById(long offerId) {
        OfferEntity offer = offerRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));
        return new OfferResponse(offer);
    }

    public List<OfferResponse> getOffers(Optional<Long> ownerId, Optional<Long> jobId) {
        if (ownerId.isPresent()) {
            if (jobId.isPresent()) {
                return offerRepository.findAllByOwnerIdAndJobId(
                        ownerId.get(), jobId.get()).stream().map(offer -> new OfferResponse(offer)).collect(Collectors.toList());
            } else {
                return offerRepository.findAllByOwnerId(ownerId.get()).stream().map(offer -> new OfferResponse(offer)).collect(Collectors.toList());
            }
        } else if (jobId.isPresent())
            return offerRepository.findAllByJobId(jobId.get()).stream().map(offer -> new OfferResponse(offer)).collect(Collectors.toList());
        else
            return offerRepository.findAll().stream().map(offer -> new OfferResponse(offer)).collect(Collectors.toList());
    }

    public OfferResponse updateOffer(long offerId, OfferRequest offerRequest) {
        OfferEntity offer = offerRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));
        offer.setDescription(offerRequest.getDescription());
        offer.setPrice(offerRequest.getPrice());
        return new OfferResponse(offerRepository.save(offer));
    }

    @Transactional
    public OfferResponse updateOfferStatus(long offerId, UpdateOfferStatusRequest updateOfferStatusRequest) {
        OfferEntity offerToChange = offerRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));

        JobEntity relatedJob = offerToChange.getJob();
        List<OfferEntity> allOffers = offerRepository.findAllByJobId(relatedJob.getId());
        OfferStatus toChangeStatus = updateOfferStatusRequest.getStatus();
        if (toChangeStatus == OfferStatus.ACCEPTED) {

            for (OfferEntity offer : allOffers) {
                if (offer.getId() == offerId) {

                    offer.setStatus(OfferStatus.ACCEPTED);
                    //We created empty work instance on DB to track the work.
                    workService.addWork(new WorkCreateRequest(offer.getOwner().getId(), offer.getJob().getId(), null, 0));

                } else {
                    offer.setStatus(OfferStatus.DECLINED);
                }
            }
            offerRepository.saveAll(allOffers);
            //We closed it to prevent multiple offers to be accepted and declined others.
            relatedJob.setStatus(JobStatus.WAITING_FINISH);
            jobRepository.save(relatedJob);
            //Also we changed job status to waiting finish to prevent to get offers after accepting one of them.

            pendingAmountService.addPendingAmount(new PendingAmountRequest(offerToChange.getOwner().getId(), offerToChange.getPrice(), offerToChange.getJob().getId()));

            return getOfferById(offerId);
        } else {
            offerToChange.setStatus(OfferStatus.DECLINED);
            return new OfferResponse(offerRepository.save(offerToChange));
        }

    }

    public void deleteOffer(long offerId) {
        OfferEntity offer = offerRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));
            offerRepository.delete(offer);
    }
}

package com.jobbud.ws.services;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.entities.OfferEntity;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.entities.WorkEntity;
import com.jobbud.ws.enums.GeneralStatus;
import com.jobbud.ws.repositories.JobRepository;
import com.jobbud.ws.repositories.OfferRepository;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.repositories.WorkRepository;
import com.jobbud.ws.requests.OfferRequest;
import com.jobbud.ws.requests.UpdateStatusRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private OfferRepository offerRepository;
    private UserRepository userRepository;
    private JobRepository jobRepository;
    private WorkRepository workRepository;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository, JobRepository jobRepository, WorkRepository workRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.workRepository = workRepository;
    }

    public OfferEntity addOffer(OfferRequest offerRequest) {

        OfferEntity offer = new OfferEntity();
        UserEntity owner = userRepository.findById(offerRequest.getOwnerId()).orElse(null);
        JobEntity job = jobRepository.findById(offerRequest.getJobId()).orElse(null);

        if (job.getStatus() == GeneralStatus.WAITING_OFFERS) {
            offer.setDescription(offerRequest.getDescription());
            offer.setStatus(offerRequest.getStatus());
            offer.setPrice(offerRequest.getPrice());
            offer.setOwner(owner);
            offer.setJob(job);
            return offerRepository.save(offer);
        }
        return null;

    }

    public OfferEntity getOfferById(long offerId) {
        return offerRepository.findById(offerId).orElse(null);
    }


    public List<OfferEntity> getOffers(Optional<Long> ownerId, Optional<Long> jobId) {
        if (ownerId.isPresent()) {
            if (jobId.isPresent()) {
                return offerRepository.findAllByOwnerIdAndJobId(ownerId.get(), jobId.get());
            } else {
                return offerRepository.findAllByOwnerId(ownerId.get());
            }
        } else if (jobId.isPresent())
            return offerRepository.findAllByJobId(jobId.get());
        else
            return offerRepository.findAll();
    }

    public OfferEntity updateOffer(long offerId, OfferRequest offerRequest) {
        OfferEntity offer = offerRepository.findById(offerId).orElse(null);
        if (offer != null) {
            offer.setDescription(offerRequest.getDescription());
            offer.setPrice(offerRequest.getPrice());
            return offerRepository.save(offer);
        } else {
            return null;
        }
    }

    public OfferEntity updateOfferStatus(long offerId, UpdateStatusRequest updateStatusRequest) {
        OfferEntity offer = offerRepository.findById(offerId).orElse(null);
        GeneralStatus toChangeStatus = updateStatusRequest.getStatus();
        if (offer != null) {
            if (toChangeStatus == GeneralStatus.ACCEPTED) {
                offer.setStatus(GeneralStatus.ACCEPTED);
                WorkEntity newWork = new WorkEntity(offer.getOwner(), offer.getJob(), null, 0);
                workRepository.save(newWork);




                return offerRepository.save(offer);
            } else {
                offer.setStatus(toChangeStatus);
                return offerRepository.save(offer);
            }
        }
        return null;
    }
}

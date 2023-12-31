package com.jobbud.ws.services;


import com.jobbud.ws.entities.*;
import com.jobbud.ws.enums.JobStatus;
import com.jobbud.ws.enums.WorkStatus;
import com.jobbud.ws.exceptions.NotFoundException;
import com.jobbud.ws.repositories.JobRepository;
import com.jobbud.ws.repositories.OfferRepository;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.repositories.WorkRepository;
import com.jobbud.ws.requests.WorkCreateRequest;
import com.jobbud.ws.requests.WorkUpdateRequest;
import com.jobbud.ws.requests.WorkUpdateStatusRequest;
import com.jobbud.ws.responses.OfferResponse;
import com.jobbud.ws.responses.WorkResponse;
import com.jobbud.ws.responses.WorkWithOfferResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkService {
    private final WorkRepository workRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final WalletService walletService;
    private final OfferRepository offerRepository;

    public WorkService(WorkRepository workRepository, JobRepository jobRepository, UserRepository userRepository, WalletService walletService, OfferRepository offerRepository) {
        this.workRepository = workRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.walletService = walletService;
        this.offerRepository = offerRepository;
    }


    public WorkEntity addWork(WorkCreateRequest workCreateRequest) {

        WorkEntity workEntity = new WorkEntity();
        UserEntity owner = userRepository.findById(workCreateRequest.getWorkerId()).orElseThrow(() -> new NotFoundException("User not found"));
        JobEntity job = jobRepository.findById(workCreateRequest.getJobId()).orElseThrow(() -> new NotFoundException("Job not found"));

        workEntity.setWorkContent(workCreateRequest.getWorkContent());
        workEntity.setStatus(workCreateRequest.getStatus());
        workEntity.setCompletedDate(workCreateRequest.getCompletedDate());
        workEntity.setWorker(owner);
        workEntity.setJob(job);

        return workRepository.save(workEntity);
    }


    public WorkResponse getWorkById(long workId) {

        WorkEntity work = workRepository.findById(workId).orElseThrow(() -> new NotFoundException("Work not found"));
        return new WorkResponse(work);
    }

    public List<WorkWithOfferResponse> getWorks(Optional<Long> workerId, Optional<WorkStatus> workStatus) {
        if (workerId.isPresent() && workStatus.isPresent()) {
            return workRepository.findAllByWorkerIdAndStatus(workerId.get(), workStatus.get())
                    .stream()
                    .map(work -> {
                        OfferResponse relatedOffer = new OfferResponse(offerRepository.findByOwnerIdAndJobId(work.getWorker().getId(), work.getJob().getId()));

                        return new WorkWithOfferResponse(work, relatedOffer);
                    })
                    .collect(Collectors.toList());
        } else if (workerId.isPresent()) {
            return workRepository.findAllByWorkerId(workerId.get())
                    .stream()
                    .map(work -> {
                        OfferResponse relatedOffer = new OfferResponse(offerRepository.findByOwnerIdAndJobId(work.getWorker().getId(), work.getJob().getId()));

                        return new WorkWithOfferResponse(work, relatedOffer);
                    })
                    .collect(Collectors.toList());
        } else if (workStatus.isPresent()) {
            return workRepository.findAllByStatus(workStatus.get())
                    .stream()
                    .map(work -> {
                        OfferResponse relatedOffer = new OfferResponse(offerRepository.findByOwnerIdAndJobId(work.getWorker().getId(), work.getJob().getId()));

                        return new WorkWithOfferResponse(work, relatedOffer);
                    })
                    .collect(Collectors.toList());
        } else {
            return workRepository.findAll()
                    .stream()
                    .map(work -> {
                        OfferResponse relatedOffer = new OfferResponse(offerRepository.findByOwnerIdAndJobId(work.getWorker().getId(), work.getJob().getId()));

                        return new WorkWithOfferResponse(work, relatedOffer);
                    })
                    .collect(Collectors.toList());
        }
    }


    public WorkResponse updateWork(long workId, WorkUpdateRequest workUpdateRequest) {

        WorkEntity workEntity = workRepository.findById(workId).orElseThrow(() -> new NotFoundException("Work not found"));

        workEntity.setWorkContent(workUpdateRequest.getWorkContent());
        workEntity.setStatus(workUpdateRequest.getStatus());
        workEntity.setCompletedDate(workUpdateRequest.getCompletedDate());
        return new WorkResponse(workRepository.save(workEntity));
    }

    public void deleteWork(long workId) {
        WorkEntity workEntity = workRepository.findById(workId).orElseThrow(() -> new NotFoundException("Work not found"));
        workRepository.delete(workEntity);
    }

    public ResponseEntity<String> updateWorkStatus(long workId, WorkUpdateStatusRequest workUpdateStatusRequest) {
        WorkEntity workEntity = workRepository.findById(workId).orElseThrow(() -> new NotFoundException("Work not found"));
        JobEntity jobEntity = jobRepository.findById(workEntity.getJob().getId()).orElseThrow(() -> new NotFoundException("Job not found"));
        WorkEntity workEntityFromDB = null;
        PendingAmountEntity pendingAmount = null;
        if (workUpdateStatusRequest.getStatus() == WorkStatus.APPROVED) {
            jobEntity.setStatus(JobStatus.FINISHED);
            workEntity.setStatus(WorkStatus.APPROVED);
            jobRepository.save(jobEntity);
            workRepository.save(workEntity);

            pendingAmount = walletService.getPendingAmountInstance(jobEntity.getId());
            //Pending amount added to freelancer's wallet
            walletService.addAmountFromPendingToWallet(workEntity.getWorker().getId(), pendingAmount);

            //Decrease pending amount from job owner
            walletService.decreaseAmountFromWallet(walletService.getWalletByUserId(jobEntity.getOwner().getId()), pendingAmount.getAmount());

            return ResponseEntity.ok("Work successfully approved and related job status updated. Also pending amount transferred to worker wallet.");
        } else if (workUpdateStatusRequest.getStatus() == WorkStatus.REJECTED) {
            workEntity.setStatus(WorkStatus.REJECTED);
            workEntityFromDB = workRepository.save(workEntity);
            workRepository.delete(workEntityFromDB);
            addWork(new WorkCreateRequest(workEntity.getWorker().getId(), workEntity.getJob().getId(), null, 0));
            return ResponseEntity.ok("Work rejected. And assigned work again to freelancer");
        }
        throw new IllegalArgumentException("Work status not updated because of invalid status");
    }

    public WorkResponse getWorkByJobId( Optional<Long> jobId) {
        WorkEntity work = workRepository.findByJobId( jobId.get());

        if (work != null) {
            return new WorkResponse(work);
        } else
            throw new NotFoundException("Work not found");

    }
}

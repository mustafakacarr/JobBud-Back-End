package com.jobbud.ws.services;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.enums.JobStatus;
import com.jobbud.ws.exceptions.NotFoundException;
import com.jobbud.ws.repositories.JobRepository;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.requests.JobRequest;
import com.jobbud.ws.requests.JobUpdateRequest;
import com.jobbud.ws.responses.JobResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {
    private JobRepository jobRepository;
    private UserRepository userRepository;
    private WalletService walletService;

    public JobService(JobRepository jobRepository, UserRepository userRepository, WalletService walletService) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.walletService = walletService;
    }

    public JobResponse addJob(JobRequest jobRequest) {
        UserEntity owner = userRepository.findById(jobRequest.getOwnerId()).orElseThrow(() -> new NotFoundException("User not found"));
        WalletEntity walletOfOwner = walletService.getWalletByUserId(owner.getId());
        if (walletOfOwner.getBalance() < jobRequest.getBudget())
            throw new IllegalArgumentException("not enough balance in your wallet");
        else {
            JobEntity jobEntity = new JobEntity();
            jobEntity.setOwner(owner);
            jobEntity.setLabel(jobRequest.getLabel());
            jobEntity.setDescription(jobRequest.getDescription());
            jobEntity.setBudget(jobRequest.getBudget());
            jobEntity.setDeadline(jobRequest.getDeadline());
            jobEntity.setStatus(jobRequest.getStatus());
            return new JobResponse(jobRepository.save(jobEntity));
        }
    }

    public JobResponse getJobById(long jobId) {
        JobEntity job = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Job not found"));
        return new JobResponse(job);
    }

    public List<JobResponse> getJobs(Optional<Long> ownerId, Optional<JobStatus> jobStatus) {

        if (ownerId.isPresent() && jobStatus.isPresent())
            return jobRepository.findAllByOwnerIdAndStatus(ownerId.get(), jobStatus.get()).stream().map(job -> new JobResponse(job)).collect(Collectors.toList());
        else if (ownerId.isPresent())
            return jobRepository.findByOwnerId(ownerId.get()).stream().map(job -> new JobResponse(job)).collect(Collectors.toList());
        else if (jobStatus.isPresent())
            return jobRepository.findAllByStatus(jobStatus.get()).stream().map(job -> new JobResponse(job)).collect(Collectors.toList());
        return jobRepository.findAll().stream().map(job -> new JobResponse(job)).collect(Collectors.toList());
    }

    public JobResponse updateJob(long jobId, JobUpdateRequest jobRequest) {
        JobEntity jobEntity = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Job not found"));
        jobEntity.setLabel(jobRequest.getLabel());
        jobEntity.setDescription(jobRequest.getDescription());
        jobEntity.setBudget(jobRequest.getBudget());
        jobEntity.setDeadline(jobRequest.getDeadline());
        return new JobResponse(jobRepository.save(jobEntity));
    }

    public void deleteJob(long jobId) {
        JobEntity job = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Job not found"));
        if (job != null)
            jobRepository.delete(job);
    }

    public List<JobResponse> searchJobs(String query) {
        List<JobEntity> allJobs = jobRepository.findAll();

        return allJobs.stream()
                .filter(job -> job.getLabel().toLowerCase().contains(query.toLowerCase()) ||
                        job.getDescription().toLowerCase().contains(query.toLowerCase()))
                .map(job -> new JobResponse(job)).collect(Collectors.toList());
    }
}

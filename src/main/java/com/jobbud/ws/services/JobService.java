package com.jobbud.ws.services;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.entities.OfferEntity;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.repositories.JobRepository;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.requests.JobRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private JobRepository jobRepository;
    private UserRepository userRepository;


    public JobService(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public JobEntity addJob(JobRequest jobRequest) {
        JobEntity jobEntity = new JobEntity();
        return getJobEntity(jobRequest, jobEntity);
    }

    public JobEntity getJobById(long jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }

    public List<JobEntity> getJobs(Optional<Long> ownerId) {
        if (ownerId.isPresent())
            return jobRepository.findByOwnerId(ownerId.get());
        else
            return jobRepository.findAll();
    }

    public JobEntity updateJob(long jobId, JobRequest jobRequest) {
        //We need to refactor this method with different request class later. Update and add requests should be different.
        JobEntity jobEntity = jobRepository.findById(jobId).orElse(null);
        if (jobEntity == null)
            return null;
        return getJobEntity(jobRequest, jobEntity);
    }


    private JobEntity getJobEntity(JobRequest jobRequest, JobEntity jobEntity) {
        UserEntity owner = userRepository.findById(jobRequest.getOwnerId()).orElse(null);
        jobEntity.setOwner(owner);
        jobEntity.setLabel(jobRequest.getLabel());
        jobEntity.setDescription(jobRequest.getDescription());
        jobEntity.setBudget(jobRequest.getBudget());
        jobEntity.setDeadline(jobRequest.getDeadline());
        jobEntity.setStatus(jobRequest.getStatus());
        return jobRepository.save(jobEntity);
    }

    public void deleteJob(long jobId) {
        JobEntity job = jobRepository.findById(jobId).orElse(null);
        if (job != null)
            jobRepository.delete(job);
    }
}

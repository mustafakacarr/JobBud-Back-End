package com.jobbud.ws.services;

import com.jobbud.ws.entities.JobEntity;
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
        UserEntity owner=userRepository.findById(jobRequest.getOwnerId()).orElse(null);
        jobEntity.setOwner(owner);
        jobEntity.setLabel(jobRequest.getLabel());
        jobEntity.setDescription(jobRequest.getDescription());
        jobEntity.setBudget(jobRequest.getBudget());
        jobEntity.setDeadline(jobRequest.getDeadline());
        jobEntity.setStatus(jobRequest.getStatus());
        return jobRepository.save(jobEntity);
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

}

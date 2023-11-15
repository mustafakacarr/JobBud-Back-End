package com.jobbud.ws.services;


import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.entities.WorkEntity;
import com.jobbud.ws.repositories.JobRepository;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.repositories.WorkRepository;
import com.jobbud.ws.requests.WorkRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkService {
    private final WorkRepository workRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;


    public WorkService(WorkRepository workRepository, JobRepository jobRepository, UserRepository userRepository) {
        this.workRepository = workRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }


    public WorkEntity addWork(WorkRequest workRequest) {
        WorkEntity workEntity = new WorkEntity();
        UserEntity owner = userRepository.findById(workRequest.getWorkerId()).orElse(null);
        JobEntity job = jobRepository.findById(workRequest.getJobId()).orElse(null);

        workEntity.setWorkContent(workRequest.getWorkContent());
        workEntity.setStatus(workRequest.getStatus());
        workEntity.setCompletedDate(workRequest.getCompletedDate());
        workEntity.setWorker(owner);
        workEntity.setJob(job);

        return workRepository.save(workEntity);
    }

    public WorkEntity getWorkById(long workId) {
        return workRepository.findById(workId).orElse(null);
    }

    public List<WorkEntity> getWorks(Optional<Long> workerId) {
        if(workerId.isPresent())
            return workRepository.findAllByWorkerId(workerId.get());
        else return workRepository.findAll();

    }

    public WorkEntity updateWork(long workId, WorkRequest workRequest) {
        WorkEntity workEntity = workRepository.findById(workId).orElse(null);
        if (workEntity != null) {
            workEntity.setWorkContent(workRequest.getWorkContent());
            workEntity.setStatus(workRequest.getStatus());
            workEntity.setCompletedDate(workRequest.getCompletedDate());
            return workRepository.save(workEntity);
        }
        return null;
    }
    public WorkEntity deleteWork(long workId) {
        WorkEntity workEntity = workRepository.findById(workId).orElse(null);
        if (workEntity != null) {
            workEntity.setDeleted(true);
            return workRepository.save(workEntity);
        }
        return null;
    }
}

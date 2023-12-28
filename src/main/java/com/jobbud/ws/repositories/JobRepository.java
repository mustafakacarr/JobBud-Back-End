package com.jobbud.ws.repositories;

import com.jobbud.ws.entities.JobEntity;
import com.jobbud.ws.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

    List<JobEntity> findByOwnerId(Long aLong);

    List<JobEntity> findAllByOwnerIdAndStatus(Long aLong, JobStatus jobStatus);

    List<JobEntity> findAllByStatus(JobStatus jobStatus);
}

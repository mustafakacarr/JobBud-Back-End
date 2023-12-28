package com.jobbud.ws.repositories;


import com.jobbud.ws.entities.WorkEntity;
import com.jobbud.ws.enums.WorkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkRepository extends JpaRepository<WorkEntity, Long> {

    List<WorkEntity> findAllByWorkerId(Long aLong);

    List<WorkEntity> findAllByWorkerIdAndStatus(Long aLong, WorkStatus workStatus);

    List<WorkEntity> findAllByStatus(WorkStatus workStatus);
}

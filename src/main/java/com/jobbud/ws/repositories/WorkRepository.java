package com.jobbud.ws.repositories;


import com.jobbud.ws.entities.WorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<WorkEntity, Long> {

    List<WorkEntity> findAllByWorkerId(Long aLong);
}

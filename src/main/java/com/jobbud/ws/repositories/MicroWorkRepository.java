package com.jobbud.ws.repositories;

import com.jobbud.ws.entities.MicroWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroWorkRepository  extends JpaRepository<MicroWorkEntity,Long> {


    boolean existsByCompletedPersonId(long freelancerId);

    boolean existsByCompletedPersonIdAndMicroTransactionId(long freelancerId, Long microTransactionId);
}

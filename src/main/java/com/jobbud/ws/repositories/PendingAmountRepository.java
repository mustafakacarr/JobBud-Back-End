package com.jobbud.ws.repositories;

import com.jobbud.ws.entities.PendingAmountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingAmountRepository extends JpaRepository<PendingAmountEntity, Long> {
}

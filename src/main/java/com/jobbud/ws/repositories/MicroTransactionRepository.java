package com.jobbud.ws.repositories;

import com.jobbud.ws.entities.MicroTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroTransactionRepository extends JpaRepository<MicroTransactionEntity,Long>{

}

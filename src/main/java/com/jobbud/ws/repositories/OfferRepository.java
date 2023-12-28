package com.jobbud.ws.repositories;

import com.jobbud.ws.entities.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long>  {

    List<OfferEntity> findAllByOwnerIdAndJobId(Long aLong, Long aLong1);

    List<OfferEntity> findAllByOwnerId(Long aLong);

    List<OfferEntity> findAllByJobId(Long aLong);



    OfferEntity findByOwnerIdAndJobId(long workerId, long jobId);
}

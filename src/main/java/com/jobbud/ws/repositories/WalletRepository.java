package com.jobbud.ws.repositories;

import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.responses.WalletHistoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity,Long> {
    WalletEntity findByUserId(long userId);

}

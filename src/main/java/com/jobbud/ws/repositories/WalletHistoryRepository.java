package com.jobbud.ws.repositories;

import com.jobbud.ws.entities.WalletHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WalletHistoryRepository extends JpaRepository<WalletHistoryEntity, Long> {
   List<WalletHistoryEntity> findByWalletId(long walletId);
}

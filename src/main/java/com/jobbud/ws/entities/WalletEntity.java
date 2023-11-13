package com.jobbud.ws.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "wallets")
public class WalletEntity {
    public WalletEntity() {
    }

    public WalletEntity(UserEntity user) {
        this.balance = 0;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private float balance;

    @OneToOne
    @JsonIgnore
    private UserEntity user;

    @OneToMany(mappedBy = "wallet",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PendingAmountEntity> pendingAmounts;

}

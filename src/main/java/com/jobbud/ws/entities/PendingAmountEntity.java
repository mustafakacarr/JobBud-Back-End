package com.jobbud.ws.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "pending_amounts")
public class PendingAmountEntity {
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private long id;
    private float amount;
    @OneToOne
    private JobEntity job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;
    private long createdAt=new Date().getTime();
}

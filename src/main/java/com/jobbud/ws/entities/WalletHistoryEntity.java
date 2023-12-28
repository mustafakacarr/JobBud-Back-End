package com.jobbud.ws.entities;

import com.jobbud.ws.enums.WalletActionType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "wallet_history")
public class WalletHistoryEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    @ManyToOne
    private WalletEntity wallet;
    private float amount;
    private String description;
    private long date=new Date().getTime();
    @Enumerated(EnumType.STRING)
    private WalletActionType actionType;
}

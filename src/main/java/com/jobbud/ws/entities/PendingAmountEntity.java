package com.jobbud.ws.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Data
@Table(name = "pending_amounts")
@SQLDelete(sql = "UPDATE pending_amounts SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
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

    private boolean isDeleted = false;
}

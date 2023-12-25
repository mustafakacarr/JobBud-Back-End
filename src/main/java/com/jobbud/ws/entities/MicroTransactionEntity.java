package com.jobbud.ws.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "micro_transactions")
public class MicroTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private UserEntity owner;
    private String channelId;

    private String label;
    private String description;
    private int quota;
    private int numberDone=0;
    private int budget;


}

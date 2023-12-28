package com.jobbud.ws.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "micro_works")
public class MicroWorkEntity{
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private MicroTransactionEntity microTransaction;

    @ManyToOne
    private UserEntity completedPerson;

    private long completedDate=new Date().getTime();
}

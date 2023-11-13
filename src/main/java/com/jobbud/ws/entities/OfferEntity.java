package com.jobbud.ws.entities;

import com.jobbud.ws.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "offers")
public class OfferEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private float price;

    private String description;
    @ManyToOne
    private UserEntity owner;
    @ManyToOne
    private JobEntity job;
    private final long datetime = new Date().getTime();
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;
}

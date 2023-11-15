package com.jobbud.ws.entities;

import com.jobbud.ws.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Data
@Table(name = "offers")
@SQLDelete(sql = "UPDATE offers SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
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
    private OfferStatus status;

     private boolean isDeleted = false;

}

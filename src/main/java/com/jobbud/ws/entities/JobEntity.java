package com.jobbud.ws.entities;

import com.jobbud.ws.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "jobs")
public class JobEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private String label;
    private String description;
    private float budget;
    @OneToOne
    private UserEntity owner;

    private final long createdDate = new Date().getTime();
    private long deadline;

    @Enumerated(EnumType.STRING)
    private GeneralStatus status;

}

package com.jobbud.ws.entities;

import com.jobbud.ws.enums.JobStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Data
@Table(name = "jobs")
@SQLDelete(sql = "UPDATE jobs SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
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
    private JobStatus status;


    //soft delete
    private boolean isDeleted = false;
}

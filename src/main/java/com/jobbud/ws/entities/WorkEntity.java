package com.jobbud.ws.entities;

import com.jobbud.ws.enums.WorkStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@Table(name = "works")
@SQLDelete(sql = "UPDATE works SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class WorkEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    @ManyToOne
    private UserEntity worker;
    @OneToOne
    private JobEntity job;
    private String workContent;
    private long completedDate;
    @Enumerated(EnumType.STRING)
    private WorkStatus status;

    private boolean isDeleted = false;

    public WorkEntity(UserEntity worker, JobEntity job, String workContent, long completedDate) {
        this.worker = worker;
        this.job = job;
        this.workContent = workContent;
        this.completedDate = completedDate;
        this.status = WorkStatus.WAITING_FINISH;
    }

    public WorkEntity() {
    }
}

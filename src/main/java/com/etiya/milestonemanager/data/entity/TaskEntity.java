package com.etiya.milestonemanager.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Log4j2
@Entity
@Table(name = "tasks")
public class TaskEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", unique = true, nullable = false, updatable = false)
    private Long taskID;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "man_days", nullable = false)
    private int manDays;

    @Column(name = "cost", nullable = false)
    private long cost;

    @Column(name = "severity", nullable = false)
    private int severity;  // Changed to int

    @Column(name = "progress", nullable = false)
    private double progress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<AlertEntity> alerts;

    @ManyToMany
    @JoinTable(
            name = "user_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> users;
}

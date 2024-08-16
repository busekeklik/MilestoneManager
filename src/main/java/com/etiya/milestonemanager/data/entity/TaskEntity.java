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
    private int severity;

    @Column(name = "progress", nullable = false)
    private double progress;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToMany
    @JoinTable(
            name = "task_dependencies",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "dependent_task_id")
    )
    private List<TaskEntity> dependencies;

    @ManyToMany
    @JoinTable(
            name = "task_analysts",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> analysts;

    @ManyToMany
    @JoinTable(
            name = "task_solution_architects",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> solutionArchitects;

    @ManyToMany
    @JoinTable(
            name = "task_software_architects",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> softwareArchitects;
}

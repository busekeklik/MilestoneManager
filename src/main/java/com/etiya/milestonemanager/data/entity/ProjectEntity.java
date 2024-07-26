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
@Table(name = "projects")
public class ProjectEntity implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", unique = true, nullable = false, updatable = false)
    private Long projectId;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "relationProjectEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<TaskEntity> relationTaskEntityList;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    private List<TeamEntity> teams;

}

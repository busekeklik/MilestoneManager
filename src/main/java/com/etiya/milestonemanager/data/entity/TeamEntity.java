package com.etiya.milestonemanager.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Log4j2

@Entity
@Table(name = "teams")
public class TeamEntity implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", unique = true, nullable = false, updatable = false)
    private Long teamID;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "team_project", // Name of the join table
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    )
    private List<ProjectEntity> projects;
}

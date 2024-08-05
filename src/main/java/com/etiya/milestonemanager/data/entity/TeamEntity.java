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
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", unique = true, nullable = false, updatable = false)
    private Long teamID;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "team_project",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<ProjectEntity> projects;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<UserEntity> users;
}

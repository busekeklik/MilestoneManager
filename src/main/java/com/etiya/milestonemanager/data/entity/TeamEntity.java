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
    public static final Long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", unique = true, nullable = false, updatable = false)
    private Long teamID;

    @Column(name = "team_name",nullable = false)
    private String teamName;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "project_id",nullable = false)
    private Long projectID;

    @OneToMany(mappedBy = "relationTeamEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<UserEntity> relationUserEntityList;

    @OneToMany(mappedBy = "relationTeamEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ProjectEntity> relationProjectEntityList;

}

package com.etiya.milestonemanager.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@Log4j2
@Entity
@Table(name = "team_project")
@IdClass(TeamProjectId.class)
public class TeamProjectEntity {

    @Id
    @Column(name = "team_id", nullable = false)
    private Long teamID;

    @Id
    @Column(name = "project_id", nullable = false)
    private Long projectID;
}

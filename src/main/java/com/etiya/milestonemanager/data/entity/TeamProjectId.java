package com.etiya.milestonemanager.data.entity;

import java.io.Serializable;
import java.util.Objects;

public class TeamProjectId implements Serializable {
    private Long teamID;
    private Long projectID;

    // Default constructor
    public TeamProjectId() {}

    // Parameterized constructor
    public TeamProjectId(Long teamID, Long projectID) {
        this.teamID = teamID;
        this.projectID = projectID;
    }

    // Equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamProjectId that = (TeamProjectId) o;
        return Objects.equals(teamID, that.teamID) && Objects.equals(projectID, that.projectID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamID, projectID);
    }
}

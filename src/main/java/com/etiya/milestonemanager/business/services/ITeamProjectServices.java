package com.etiya.milestonemanager.business.services;

import com.etiya.milestonemanager.business.dto.TeamProjectDto;

import java.util.List;

public interface ITeamProjectServices {
    void teamProjectServiceDeleteAllData();
    TeamProjectDto teamProjectServiceCreate(TeamProjectDto teamProjectDto);
    List<TeamProjectDto> teamProjectServiceList();
    TeamProjectDto teamProjectServiceFindById(Long teamID, Long projectID);
    TeamProjectDto teamProjectServiceUpdateById(Long teamID, Long projectID, TeamProjectDto teamProjectDto);
    void teamProjectServiceDeleteById(Long teamID, Long projectID);
    List<TeamProjectDto> teamProjectServiceFindByProjectId(Long projectId);
}

package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.business.dto.TeamProjectDto;
import com.etiya.milestonemanager.business.services.ITeamProjectServices;
import com.etiya.milestonemanager.data.entity.TeamProjectEntity;
import com.etiya.milestonemanager.data.entity.TeamProjectId;
import com.etiya.milestonemanager.data.repository.TeamProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamProjectServicesImpl implements ITeamProjectServices {
    private final TeamProjectRepository teamProjectRepository;

    @Override
    public void teamProjectServiceDeleteAllData() {
        teamProjectRepository.deleteAll();
    }

    @Override
    public TeamProjectDto teamProjectServiceCreate(TeamProjectDto teamProjectDto) {
        TeamProjectEntity teamProjectEntity = new TeamProjectEntity();
        teamProjectEntity.setTeamID(teamProjectDto.getTeamID());
        teamProjectEntity.setProjectID(teamProjectDto.getProjectID());
        teamProjectRepository.save(teamProjectEntity);
        return teamProjectDto;
    }

    @Override
    public List<TeamProjectDto> teamProjectServiceList() {
        return teamProjectRepository.findAll().stream()
                .map(entity -> {
                    TeamProjectDto dto = new TeamProjectDto();
                    dto.setTeamID(entity.getTeamID());
                    dto.setProjectID(entity.getProjectID());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TeamProjectDto teamProjectServiceFindById(Long teamID, Long projectID) {
        Optional<TeamProjectEntity> teamProjectEntity = teamProjectRepository.findById(new TeamProjectId(teamID, projectID));
        if (teamProjectEntity.isPresent()) {
            TeamProjectDto dto = new TeamProjectDto();
            dto.setTeamID(teamProjectEntity.get().getTeamID());
            dto.setProjectID(teamProjectEntity.get().getProjectID());
            return dto;
        }
        return null;
    }

    @Override
    public TeamProjectDto teamProjectServiceUpdateById(Long teamID, Long projectID, TeamProjectDto teamProjectDto) {
        Optional<TeamProjectEntity> teamProjectEntityOptional = teamProjectRepository.findById(new TeamProjectId(teamID, projectID));
        if (teamProjectEntityOptional.isPresent()) {
            TeamProjectEntity teamProjectEntity = teamProjectEntityOptional.get();
            teamProjectEntity.setTeamID(teamProjectDto.getTeamID());
            teamProjectEntity.setProjectID(teamProjectDto.getProjectID());
            teamProjectRepository.save(teamProjectEntity);
            return teamProjectDto;
        }
        return null;
    }

    @Override
    public void teamProjectServiceDeleteById(Long teamID, Long projectID) {
        teamProjectRepository.deleteById(new TeamProjectId(teamID, projectID));
    }

    @Override
    public List<TeamProjectDto> teamProjectServiceFindByProjectId(Long projectId) {
        return teamProjectRepository.findByProjectID(projectId).stream()
                .map(entity -> {
                    TeamProjectDto dto = new TeamProjectDto();
                    dto.setTeamID(entity.getTeamID());
                    dto.setProjectID(entity.getProjectID());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

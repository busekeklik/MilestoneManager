package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.TeamDto;
import com.etiya.milestonemanager.business.services.ITeamServices;
import com.etiya.milestonemanager.data.entity.ProjectEntity;
import com.etiya.milestonemanager.data.entity.TeamEntity;
import com.etiya.milestonemanager.data.repository.IProjectRepository;
import com.etiya.milestonemanager.data.repository.ITeamRepository;
import com.etiya.milestonemanager.exception.Auth404Exception;
import com.etiya.milestonemanager.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//lombok
@RequiredArgsConstructor
@Log4j2

@Service
public class TeamServicesImpl implements ITeamServices<TeamDto, TeamEntity> {

    private final ModelMapperBean modelMapperBean;
    private final ITeamRepository iTeamRepository;
    private final IProjectRepository iProjectRepository;

    @Override
    public TeamDto entityToDto(TeamEntity teamEntity) {
        TeamDto teamDto = modelMapperBean.getModelMapperMethod().map(teamEntity, TeamDto.class);
        if (teamEntity.getProjects() != null) {
            List<Long> projectIds = teamEntity.getProjects().stream()
                    .map(ProjectEntity::getProjectId)
                    .collect(Collectors.toList());
            teamDto.setProjectIds(projectIds);
        }
        return teamDto;
    }

    @Override
    public TeamEntity dtoToEntity(TeamDto teamDto) {
        TeamEntity teamEntity = modelMapperBean.getModelMapperMethod().map(teamDto, TeamEntity.class);
        if (teamDto.getProjectIds() != null) {
            List<ProjectEntity> projects = (List<ProjectEntity>) iProjectRepository.findAllById(teamDto.getProjectIds());
            teamEntity.setProjects(projects);
        }
        return teamEntity;
    }

    @Override
    public void teamServiceDeleteAllData() {
        iTeamRepository.deleteAll();
    }

    @Override
    @Transactional
    public TeamDto teamServiceCreate(TeamDto teamDto) {
        if (teamDto != null) {
            TeamEntity teamEntity = dtoToEntity(teamDto);
            teamEntity = iTeamRepository.save(teamEntity); // Save entity to DB
            return entityToDto(teamEntity);
        }
        return null;
    }

    @Override
    public List<TeamDto> teamServiceList() {
        Iterable<TeamEntity> teamEntities = iTeamRepository.findAll();
        List<TeamDto> teamDtoList = new ArrayList<>();
        for (TeamEntity e : teamEntities) {
            TeamDto teamDto = entityToDto(e);
            teamDtoList.add(teamDto);
        }
        return teamDtoList;
    }

    @Override
    public TeamDto teamServiceFindById(Long id) {
        if (id == null) {
            throw new GeneralException("team id is null");
        }
        TeamEntity teamEntity = iTeamRepository.findById(id)
                .orElseThrow(() -> new Auth404Exception(id + " bulunamadı!"));
        return entityToDto(teamEntity);
    }

    @Override
    @Transactional
    public TeamDto teamServiceUpdateById(Long id, TeamDto teamDto) {
        TeamDto updateTeamDto = teamServiceFindById(id);
        if (updateTeamDto != null && teamDto != null) {
            TeamEntity teamEntity = iTeamRepository.findById(id).orElse(null);
            if (teamEntity != null) {
                teamEntity.setTeamName(teamDto.getTeamName());
                teamEntity.setDescription(teamDto.getDescription());

                if (teamDto.getProjectIds() != null) {
                    List<ProjectEntity> projects = (List<ProjectEntity>) iProjectRepository.findAllById(teamDto.getProjectIds());
                    teamEntity.setProjects(projects);
                }

                teamEntity = iTeamRepository.save(teamEntity);

                updateTeamDto.setTeamName(teamEntity.getTeamName());
                updateTeamDto.setDescription(teamEntity.getDescription());
                updateTeamDto.setProjectIds(teamEntity.getProjects().stream()
                        .map(ProjectEntity::getProjectId)
                        .collect(Collectors.toList()));
            }
            return updateTeamDto;
        }
        return null;
    }

    @Override
    @Transactional
    public TeamDto teamServiceDeleteById(Long id) {
        TeamDto teamDto = teamServiceFindById(id);
        if (teamDto != null) {
            iTeamRepository.deleteById(id);
            return teamDto;
        }
        return null;
    }
}

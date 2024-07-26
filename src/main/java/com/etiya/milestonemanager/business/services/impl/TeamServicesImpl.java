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
        return modelMapperBean.getModelMapperMethod().map(teamEntity, TeamDto.class);
    }

    @Override
    public TeamEntity dtoToEntity(TeamDto teamDto) {
        return modelMapperBean.getModelMapperMethod().map(teamDto, TeamEntity.class);
    }

    @Override
    public void teamServiceDeleteAllData() {
        iTeamRepository.deleteAll();
    }

    @Override
    @Transactional
    public TeamDto teamServiceCreate(TeamDto teamDto) {
        if (teamDto != null) {
            // Convert DTO to Entity
            TeamEntity teamEntity = dtoToEntity(teamDto);

            // Update DTO with the entity's data
            teamDto.setTeamName(teamEntity.getTeamName());
            teamDto.setDescription(teamEntity.getDescription());

            // Assuming dtoToEntity properly maps the associated projects
            if (teamEntity.getProjects() != null) {
                List<Long> projectIds = teamEntity.getProjects().stream()
                        .map(ProjectEntity::getProjectId)
                        .collect(Collectors.toList());
                teamDto.setProjectIds(projectIds);
            }

            return teamDto;
        }
        return null;
    }


    @Override
    public List<TeamDto> teamServiceList() {
        Iterable<TeamEntity> teamEntities = iTeamRepository.findAll();
        List<TeamDto> teamDtoList = new ArrayList<>();
        for(TeamEntity e: teamEntities){
            TeamDto teamDto = entityToDto(e);
            teamDtoList.add(teamDto);
        }
        return teamDtoList;
    }

    @Override
    public TeamDto teamServiceFindById(Long id) {
        TeamEntity teamEntity = null;
        if(id != null){
            teamEntity = iTeamRepository.findById(id)
                    .orElseThrow(() -> new Auth404Exception(id + " bulunamadı!"));
        }
        else if(id == null){
            throw new GeneralException("team id is null");
        }
        return entityToDto(teamEntity);
    }

    @Override
    @Transactional
    public TeamDto teamServiceUpdateById(Long id, TeamDto teamDto) {
        TeamDto updateTeamDto = teamServiceFindById(id);
        if (updateTeamDto != null && teamDto != null) {
            // Fetch the existing team entity
            TeamEntity teamEntity = iTeamRepository.findById(id).orElse(null);
            if (teamEntity != null) {
                // Update simple fields
                teamEntity.setTeamName(teamDto.getTeamName());
                teamEntity.setDescription(teamDto.getDescription());

                // Handle associated projects
                if (teamDto.getProjectIds() != null) {
                    // Fetch corresponding projects from the database
                    List<ProjectEntity> projects = (List<ProjectEntity>) iProjectRepository.findAllById(teamDto.getProjectIds());
                    teamEntity.setProjects(projects); // Set the new list of projects
                }

                // Save the updated entity
                teamEntity = iTeamRepository.save(teamEntity);

                // Update DTO to return
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
        if(teamDto != null){
            iTeamRepository.deleteById(id);
            return teamDto;
        }
        return null;
    }
}

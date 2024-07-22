package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.business.dto.ProjectDto;
import com.etiya.milestonemanager.business.services.IProjectServices;
import com.etiya.milestonemanager.data.entity.ProjectEntity;

import java.util.List;

public class ProjectServicesImpl implements IProjectServices<ProjectDto, ProjectEntity> {
    @Override
    public ProjectDto entityToDto(ProjectEntity projectEntity) {
        if (projectEntity == null) {
            return null;
        }
        return new ProjectDto(
                projectEntity.getProjectId(),
                projectEntity.getProjectName(),
                projectEntity.getStartDate(),
                projectEntity.getEndDate(),
                projectEntity.getStatus()
        );
    }

    @Override
    public ProjectEntity dtoToEntity(ProjectDto projectDto) {
        if (projectDto == null) {
            return null;
        }
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(projectDto.getProjectId());
        projectEntity.setProjectName(projectDto.getProjectName());
        projectEntity.setStartDate(projectDto.getStartDate());
        projectEntity.setEndDate(projectDto.getEndDate());
        projectEntity.setStatus(projectDto.getStatus());
        return projectEntity;
    }

    @Override
    public String projectServiceDeleteAllData() {
        return null;
    }

    @Override
    public ProjectDto projectServiceCreate(ProjectDto projectDto) {
        return null;
    }

    @Override
    public List<ProjectDto> projectServiceList() {
        return null;
    }

    @Override
    public ProjectDto projectServiceFindById(Long id) {
        return null;
    }

    @Override
    public ProjectDto projectServiceUpdateById(Long id, ProjectDto projectDto) {
        return null;
    }

    @Override
    public ProjectDto projectServiceDeleteById(Long id) {
        return null;
    }
}

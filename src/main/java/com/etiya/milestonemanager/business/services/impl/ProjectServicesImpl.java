package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.ProjectDto;
import com.etiya.milestonemanager.business.services.IProjectServices;
import com.etiya.milestonemanager.data.entity.ProjectEntity;
import com.etiya.milestonemanager.data.repository.IProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

//lombok
@RequiredArgsConstructor
@Log4j2

@Service
public class ProjectServicesImpl implements IProjectServices<ProjectDto, ProjectEntity> {

    private final ModelMapperBean modelMapperBean;
    private final IProjectRepository iProjectRepository;
    @Override
    public ProjectDto entityToDto(ProjectEntity projectEntity) {
        return modelMapperBean.getModelMapperMethod().map(projectEntity, ProjectDto.class);

    }

    @Override
    public ProjectEntity dtoToEntity(ProjectDto projectDto) {
        return modelMapperBean.getModelMapperMethod().map(projectDto, ProjectEntity.class);
    }

    @Override
    public void projectServiceDeleteAllData() {
        iProjectRepository.deleteAll();
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

package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.ProjectDto;
import com.etiya.milestonemanager.business.services.IProjectServices;
import com.etiya.milestonemanager.data.entity.ProjectEntity;
import com.etiya.milestonemanager.data.repository.IProjectRepository;
import com.etiya.milestonemanager.exception.Auth404Exception;
import com.etiya.milestonemanager.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Transactional
    public ProjectDto projectServiceCreate(ProjectDto projectDto) {
        if(projectDto != null){
            ProjectEntity projectEntity = dtoToEntity(projectDto);
            projectDto.setProjectName(projectEntity.getProjectName());
            projectDto.setStatus(projectEntity.getStatus());
            projectDto.setStartDate(projectEntity.getStartDate());
            projectDto.setEndDate(projectEntity.getEndDate());
            return projectDto;
        }
        return null;
    }

    @Override
    public List<ProjectDto> projectServiceList() {
        Iterable<ProjectEntity> projectEntities = iProjectRepository.findAll();
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for(ProjectEntity e: projectEntities){
            ProjectDto projectDto = entityToDto(e);
            projectDtoList.add(projectDto);
        }
        return projectDtoList;
    }

    @Override
    public ProjectDto projectServiceFindById(Long id) {
        ProjectEntity projectEntity = null;
        if(id != null){
            projectEntity = iProjectRepository.findById(id).
                    orElseThrow(()->new Auth404Exception(id + "nolu veri yoktur"));
        }
        else if(id == null)
            throw new GeneralException("project id null");
        return entityToDto(projectEntity);
    }

    @Override
    public ProjectDto projectServiceUpdateById(Long id, ProjectDto projectDto) {
        ProjectDto updateProjectDto = projectServiceFindById(id);
        if(updateProjectDto != null){
            ProjectEntity projectEntity = dtoToEntity(updateProjectDto);
            projectEntity.setProjectName(projectDto.getProjectName());
            projectEntity.setStatus(projectDto.getStatus());
            projectEntity.setStartDate(projectDto.getStartDate());
            projectEntity.setEndDate(projectDto.getEndDate());
            iProjectRepository.save(projectEntity);
            return updateProjectDto;
        }
        return null;
    }

    @Override
    @Transactional
    public ProjectDto projectServiceDeleteById(Long id) {
        ProjectDto projectDto = projectServiceFindById(id);
        if(projectDto != null){
            iProjectRepository.deleteById(id);
            return projectDto;
        }
        return null;
    }
}

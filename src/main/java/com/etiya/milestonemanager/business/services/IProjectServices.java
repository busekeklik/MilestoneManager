package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface IProjectServices<Dto, Entity> {
    Dto entityToDto(Entity entity);
    Entity dtoToEntity(Dto dto);
    String projectServiceDeleteAllData();
    Dto projectServiceCreate(Dto dto);
    List<Dto> projectServiceList();
    Dto projectServiceFindById(Long id);
    Dto projectServiceUpdateById(Long id, Dto dto);
    Dto projectServiceDeleteById(Long id);
}

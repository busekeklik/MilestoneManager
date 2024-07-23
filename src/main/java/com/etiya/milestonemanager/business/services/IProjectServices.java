package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface IProjectServices<Dto, Entity> {

    public Dto entityToDto(Entity entity);

    public Entity dtoToEntity(Dto dto);

    public void projectServiceDeleteAllData();


    public Dto projectServiceCreate(Dto dto);
    public List<Dto> projectServiceList();
    public Dto projectServiceFindById(Long id);
    public Dto projectServiceUpdateById(Long id, Dto dto);
    public Dto projectServiceDeleteById(Long id);
}

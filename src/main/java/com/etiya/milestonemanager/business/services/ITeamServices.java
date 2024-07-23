package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface ITeamServices<D,E>{

    public D entityToDto(E e);
    public E dtoToEntity(D d);

    public void teamServiceDeleteAllData();

    // CRUD
    // CREATE
    public D teamServiceCreate(D d);

    // LIST
    public List<D> teamServiceList();

    // FIND
    public D teamServiceFindById(Long id);

    // UPDATE
    public D teamServiceUpdateById(Long id,D d);

    // DELETE
    public D teamServiceDeleteById(Long id);

}

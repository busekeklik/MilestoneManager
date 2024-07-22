package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface IUserServices<D,E>{

    public D entityToDto(E e);
    public E dtoToEntity(D d);

    public String userServiceDeleteAllData();

    // CRUD
    // CREATE
    public D userServiceCreate(D d);

    // LIST
    public List<D> userServiceList();

    // FIND
    public D userServiceFindById(Long id);

    // UPDATE
    public D userServiceUpdateById(Long id,D d);

    // DELETE
    public D userServiceDeleteById(Long id);

}

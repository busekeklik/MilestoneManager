package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface IRoleServices<D,E>{
    public D entityToDto(E e);
    public E dtoToEntity(D d);

    public void roleServiceDeleteAllData();

    // CRUD
    // CREATE
    public D roleServiceCreate(D d);

    // LIST
    public List<D> roleServiceList();

    // FIND
    public D roleServiceFindById(Long id);

    // UPDATE
    public D roleServiceUpdateById(Long id,D d);

    // DELETE
    public D roleServiceDeleteById(Long id);

}

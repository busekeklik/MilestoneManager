package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface ITaskServices<D,E> {
    public D entityToDto(E e);
    public E dtoToEntity(D d);

    public void taskServiceDeleteAllData();

    // CRUD
    // CREATE
    public D taskServiceCreate(D d);

    // LIST
    public List<D> taskServiceList();

    // FIND
    public D taskServiceFindById(Long id);

    // UPDATE
    public D taskServiceUpdateById(Long id,D d);

    // DELETE
    public D taskServiceDeleteById(Long id);

}

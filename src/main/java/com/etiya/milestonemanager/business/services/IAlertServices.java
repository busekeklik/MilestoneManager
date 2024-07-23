package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface IAlertServices<D,E>{

    public D entityToDto(E e);
    public E dtoToEntity(D d);

    public void alertServiceDeleteAllData();

    // CRUD
    // CREATE
    public D alertServiceCreate(D d);

    // LIST
    public List<D> alertServiceList();

    // FIND
    public D alertServiceFindById(Long id);

    // UPDATE
    public D alertServiceUpdateById(Long id,D d);

    // DELETE
    public D alertServiceDeleteById(Long id);

}

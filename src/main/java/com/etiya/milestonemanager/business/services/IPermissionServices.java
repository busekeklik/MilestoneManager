package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface IPermissionServices<D,E> {
    public D entityToDto(E e);
    public E dtoToEntity(D d);

    public void permissionServiceDeleteAllData();

    // CRUD
    // CREATE
    public D permissionServiceCreate(D d);

    // LIST
    public List<D> permissionServiceList();

    // FIND
    public D permissionServiceFindById(Long id);

    // UPDATE
    public D permissionServiceUpdateById(Long id,D d);

    // DELETE
    public D permissionServiceDeleteById(Long id);
}

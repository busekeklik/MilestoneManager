package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface IUserServices<D, E> {

    D entityToDto(E e);
    E dtoToEntity(D d);

    void userServiceDeleteAllData();

    // CRUD
    // CREATE
    D userServiceCreate(D d);

    // LIST
    List<D> userServiceList();

    // FIND
    D userServiceFindById(Long id);

    // UPDATE
    D userServiceUpdateById(Long id, D d);

    // DELETE
    D userServiceDeleteById(Long id);
}

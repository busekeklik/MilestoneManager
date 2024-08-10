package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface ITeamServices<D, E> {

    D entityToDto(E e);
    E dtoToEntity(D d);

    void teamServiceDeleteAllData();

    D teamServiceCreate(D d);

    List<D> teamServiceList();

    D teamServiceFindById(Long id);

    D teamServiceUpdateById(Long id, D d);

    D teamServiceDeleteById(Long id);
}

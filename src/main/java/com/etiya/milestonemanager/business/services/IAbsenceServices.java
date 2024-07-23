package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface IAbsenceServices<D,E> {
    public D entityToDto(E e);
    public E dtoToEntity(D d);
    public void absenceServiceDeleteAllData();

    // CRUD
    // CREATE
    public D absenceServiceCreate(D d);

    // LIST
    public List<D> absenceServiceList();

    // FIND
    public D absenceServiceFindById(Long id);

    // UPDATE
    public D absenceServiceUpdateById(Long id,D d);

    // DELETE
    public D absenceServiceDeleteById(Long id);
}

package com.etiya.milestonemanager.business.services;

import com.etiya.milestonemanager.business.dto.UserDto;
import com.etiya.milestonemanager.data.entity.RoleType;

import java.util.List;

public interface IUserServices<D, E> {
    D entityToDto(E e);
    E dtoToEntity(D d);

    void userServiceDeleteAllData();

    // CRUD operations
    D userServiceCreate(D d);
    List<D> userServiceList();
    D userServiceFindById(Long id);
    D userServiceUpdateById(Long id, D d);
    D userServiceDeleteById(Long id);

    // Method to find users by role
    List<D> userServiceFindByRole(RoleType role);
}

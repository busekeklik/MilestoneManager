package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.business.dto.UserDto;
import com.etiya.milestonemanager.business.services.IUserServices;
import com.etiya.milestonemanager.data.entity.UserEntity;

import java.util.List;

public class UserServicesImpl implements IUserServices<UserDto, UserEntity> {
    @Override
    public UserDto entityToDto(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity dtoToEntity(UserDto userDto) {
        return null;
    }

    @Override
    public String userServiceDeleteAllData() {
        return null;
    }

    @Override
    public UserDto userServiceCreate(UserDto userDto) {
        return null;
    }

    @Override
    public List<UserDto> userServiceList() {
        return null;
    }

    @Override
    public UserDto userServiceFindById(Long id) {
        return null;
    }

    @Override
    public UserDto userServiceUpdateById(Long id, UserDto userDto) {
        return null;
    }

    @Override
    public UserDto userServiceDeleteById(Long id) {
        return null;
    }
}

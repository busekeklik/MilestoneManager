package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.UserDto;
import com.etiya.milestonemanager.business.services.IUserServices;
import com.etiya.milestonemanager.data.entity.UserEntity;
import com.etiya.milestonemanager.data.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//lombok
@RequiredArgsConstructor
@Log4j2

@Service
public class UserServicesImpl implements IUserServices<UserDto, UserEntity> {

    private final ModelMapperBean modelMapperBean;
    private final IUserRepository iUserRepository;
    @Override
    public UserDto entityToDto(UserEntity userEntity) {
        return modelMapperBean.getModelMapperMethod().map(userEntity, UserDto.class);

    }

    @Override
    public UserEntity dtoToEntity(UserDto userDto) {
        return modelMapperBean.getModelMapperMethod().map(userDto, UserEntity.class);
    }

    @Override
    public String userServiceDeleteAllData() {
        iUserRepository.deleteAll();

    }

    @Override
    public UserDto userServiceCreate(UserDto userDto) {
        if(userDto != null){
            UserEntity userEntity = dtoToEntity(userDto);
            userDto.setUserName(userEntity.getUserName());
            userDto.setPassword(userEntity.getPassword());
            userDto.setEmail(userEntity.getEmail());
            return userDto;
        }
        return null;
    }

    @Override
    public List<UserDto> userServiceList() {
        Iterable<UserEntity> userEntities = iUserRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(UserEntity e: userEntities){
            UserDto userDto = entityToDto(e);
            userDtoList.add(userDto);
        }
        return userDtoList;
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

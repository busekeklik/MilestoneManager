package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.UserDto;
import com.etiya.milestonemanager.business.services.IUserServices;
import com.etiya.milestonemanager.data.entity.UserEntity;
import com.etiya.milestonemanager.data.repository.IUserRepository;
import com.etiya.milestonemanager.exception.Auth404Exception;
import com.etiya.milestonemanager.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class UserServicesImpl implements IUserServices<UserDto, UserEntity> {

    private final ModelMapperBean modelMapperBean;
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto entityToDto(UserEntity userEntity) {
        return modelMapperBean.getModelMapperMethod().map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity dtoToEntity(UserDto userDto) {
        return modelMapperBean.getModelMapperMethod().map(userDto, UserEntity.class);
    }

    @Override
    public void userServiceDeleteAllData() {
        iUserRepository.deleteAll();
    }

    @Override
    @Transactional
    public UserDto userServiceCreate(UserDto userDto) {
        if (userDto != null) {
            UserEntity userEntity = dtoToEntity(userDto);
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hash the password
            iUserRepository.save(userEntity);
            return entityToDto(userEntity);
        }
        return null;
    }

    @Override
    public List<UserDto> userServiceList() {
        Iterable<UserEntity> userEntities = iUserRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity e : userEntities) {
            UserDto userDto = entityToDto(e);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserDto userServiceFindById(Long id) {
        UserEntity userEntity = null;
        if (id != null) {
            userEntity = iUserRepository.findById(id)
                    .orElseThrow(() -> new Auth404Exception(id + " nolu veri yoktur"));
        } else if (id == null) {
            throw new GeneralException("user id null");
        }
        return entityToDto(userEntity);
    }

    @Override
    @Transactional
    public UserDto userServiceUpdateById(Long id, UserDto userDto) {
        UserDto updateUserDto = userServiceFindById(id);
        if (updateUserDto != null) {
            UserEntity userEntity = dtoToEntity(updateUserDto);
            userEntity.setUserName(userDto.getUserName());
            userEntity.setEmail(userDto.getEmail());
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hash the password
            userEntity.setActive(userDto.isActive());
            iUserRepository.save(userEntity);
            return entityToDto(userEntity);
        }
        return null;
    }

    @Override
    @Transactional
    public UserDto userServiceDeleteById(Long id) {
        UserDto userDto = userServiceFindById(id);
        if (userDto != null) {
            iUserRepository.deleteById(id);
            return userDto;
        }
        return null;
    }
}

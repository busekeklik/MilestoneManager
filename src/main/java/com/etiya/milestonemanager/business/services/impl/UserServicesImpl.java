package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.UserDto;
import com.etiya.milestonemanager.business.services.IUserServices;
import com.etiya.milestonemanager.data.entity.RoleType;
import com.etiya.milestonemanager.data.entity.TeamEntity;
import com.etiya.milestonemanager.data.entity.UserEntity;
import com.etiya.milestonemanager.data.repository.ITeamRepository;
import com.etiya.milestonemanager.data.repository.IUserRepository;
import com.etiya.milestonemanager.exception.Auth404Exception;
import com.etiya.milestonemanager.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServicesImpl implements IUserServices<UserDto, UserEntity> {

    private final ModelMapperBean modelMapperBean;
    private final IUserRepository iUserRepository;
    private final ITeamRepository iTeamRepository;  // Add this to fetch teams
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto entityToDto(UserEntity userEntity) {
        UserDto userDto = modelMapperBean.getModelMapperMethod().map(userEntity, UserDto.class);
        userDto.setTeamId(userEntity.getTeam().getTeamID());
        return userDto;
    }

    @Override
    public UserEntity dtoToEntity(UserDto userDto) {
        UserEntity userEntity = modelMapperBean.getModelMapperMethod().map(userDto, UserEntity.class);
        TeamEntity teamEntity = iTeamRepository.findById(userDto.getTeamId())
                .orElseThrow(() -> new GeneralException("Team not found with ID: " + userDto.getTeamId()));
        userEntity.setTeam(teamEntity);
        return userEntity;
    }

    @Override
    public void userServiceDeleteAllData() {
        iUserRepository.deleteAll();
    }

    @Override
    @Transactional
    public UserDto userServiceCreate(UserDto userDto) {
        if (userDto != null) {
            // Set default team_id if not provided
            if (userDto.getTeamId() == null) {
                userDto.setTeamId(1L);
            }

            UserEntity userEntity = dtoToEntity(userDto);

            // Hash the password
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

            // Handle multiple roles
            Set<RoleType> roles = new HashSet<>(userDto.getRoles());
            userEntity.setRoles(roles);

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
    public List<UserDto> userServiceFindByRole(RoleType role) {
        List<UserEntity> userEntities = iUserRepository.findByRolesContaining(role);
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            UserDto userDto = entityToDto(userEntity);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserDto userServiceFindById(Long id) {
        if (id == null) {
            throw new GeneralException("user id null");
        }

        UserEntity userEntity = iUserRepository.findById(id)
                .orElseThrow(() -> new Auth404Exception(id + " nolu veri yoktur"));

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

            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                userEntity.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hash the password
            }

            userEntity.setActive(userDto.isActive());

            // Update roles
            Set<RoleType> roles = new HashSet<>(userDto.getRoles());
            userEntity.setRoles(roles);

            // Set team_id, ensuring it doesn't get overwritten to null
            if (userDto.getTeamId() != null) {
                TeamEntity teamEntity = iTeamRepository.findById(userDto.getTeamId())
                        .orElseThrow(() -> new GeneralException("Team not found with ID: " + userDto.getTeamId()));
                userEntity.setTeam(teamEntity);
            }

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

package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.RoleDto;
import com.etiya.milestonemanager.business.services.IRoleServices;
import com.etiya.milestonemanager.data.entity.RoleEntity;
import com.etiya.milestonemanager.data.repository.IRoleRepository;
import com.etiya.milestonemanager.exception.Auth404Exception;
import com.etiya.milestonemanager.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//lombok
@RequiredArgsConstructor
@Log4j2

@Service
public class RoleServicesImpl implements IRoleServices<RoleDto, RoleEntity> {

    private final ModelMapperBean modelMapperBean;
    private final IRoleRepository iRoleRepository;

    @Override
    public RoleDto entityToDto(RoleEntity roleEntity) {
        return modelMapperBean.getModelMapperMethod().map(roleEntity, RoleDto.class);

    }

    @Override
    public RoleEntity dtoToEntity(RoleDto roleDto) {
        return modelMapperBean.getModelMapperMethod().map(roleDto, RoleEntity.class);
    }

    @Override
    public void roleServiceDeleteAllData() {
        iRoleRepository.deleteAll();
    }

    @Override
    @Transactional
    public RoleDto roleServiceCreate(RoleDto roleDto) {
        if(roleDto != null){
            RoleEntity roleEntity = dtoToEntity(roleDto);
            roleDto.setRoleName(roleEntity.getRoleName());
            if(roleDto.getDescription() != null){
                roleDto.setDescription(roleDto.getDescription());
            }
            return roleDto;
        }
        return null;
    }

    @Override
    public List<RoleDto> roleServiceList() {

        Iterable<RoleEntity> roleEntities = iRoleRepository.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for(RoleEntity e:roleEntities){
            RoleDto roleDto = entityToDto(e);
            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }

    @Override
    public RoleDto roleServiceFindById(Long id) {
        RoleEntity roleEntity = null;
        if(id != null){
            roleEntity = iRoleRepository.findById(id).
                    orElseThrow(()->new Auth404Exception(id + "nolu veri yoktur"));
        }
        else if(id == null){
            throw new GeneralException("role id null");
        }
        return entityToDto(roleEntity);
    }

    @Override
    @Transactional
    public RoleDto roleServiceUpdateById(Long id, RoleDto roleDto) {
        RoleDto updateRoleDto = roleServiceFindById(id);
        if(updateRoleDto != null){
            RoleEntity roleEntity = dtoToEntity(updateRoleDto);
            roleEntity.setRoleName(roleDto.getRoleName());
            roleEntity.setDescription(roleDto.getDescription());
            iRoleRepository.save(roleEntity);
            return updateRoleDto;
        }
        return null;
    }

    @Override
    @Transactional
    public RoleDto roleServiceDeleteById(Long id) {
        RoleDto roleDto = roleServiceFindById(id);
        if(roleDto != null){
            iRoleRepository.deleteById(id);
            return roleDto;
        }
        return null;
    }
}

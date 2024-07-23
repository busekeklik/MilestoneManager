package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.PermissionDto;
import com.etiya.milestonemanager.business.services.IPermissionServices;
import com.etiya.milestonemanager.data.entity.PermissionEntity;
import com.etiya.milestonemanager.data.repository.IPermissionRepository;
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
public class PermissionServicesImpl implements IPermissionServices<PermissionDto, PermissionEntity> {

    private final ModelMapperBean modelMapperBean;
    private final IPermissionRepository iPermissionRepository;

    @Override
    public PermissionDto entityToDto(PermissionEntity permissionEntity) {
        return modelMapperBean.getModelMapperMethod().map(permissionEntity, PermissionDto.class);
    }

    @Override
    public PermissionEntity dtoToEntity(PermissionDto permissionDto) {
        return modelMapperBean.getModelMapperMethod().map(permissionDto, PermissionEntity.class);
    }

    @Override
    public void permissionServiceDeleteAllData() {
        iPermissionRepository.deleteAll();
    }

    @Override
    @Transactional
    public PermissionDto permissionServiceCreate(PermissionDto permissionDto) {
        if(permissionDto != null){
            PermissionEntity permissionEntity = dtoToEntity(permissionDto);
            permissionDto.setPermissionName(permissionEntity.getPermissionName());
            permissionDto.setDescription(permissionDto.getDescription());
            return permissionDto;
        }
        return null;
    }

    @Override
    public List<PermissionDto> permissionServiceList() {
        Iterable<PermissionEntity> permissionEntities = iPermissionRepository.findAll();
        List<PermissionDto> permissionDtoList = new ArrayList<>();
        for(PermissionEntity e:permissionEntities){
            PermissionDto permissionDto = entityToDto(e);
            permissionDtoList.add(permissionDto);
        }
        return permissionDtoList;
    }

    @Override
    public PermissionDto permissionServiceFindById(Long id) {
        PermissionEntity permissionEntity = null;
        if(id != null){
            permissionEntity = iPermissionRepository.findById(id).
                    orElseThrow(()->new Auth404Exception(id + "nolu veri yoktur."));
        }
        else if(id == null){
            throw new GeneralException("task id null");
        }
        return entityToDto(permissionEntity);
    }

    @Override
    @Transactional
    public PermissionDto permissionServiceUpdateById(Long id, PermissionDto permissionDto) {
        PermissionDto updatePermissionDto = permissionServiceFindById(id);
        if(updatePermissionDto != null){
            PermissionEntity permissionEntity = dtoToEntity(updatePermissionDto);
            permissionEntity.setPermissionName(permissionDto.getPermissionName());
            permissionEntity.setDescription(permissionDto.getDescription());
            iPermissionRepository.save(permissionEntity);
            return updatePermissionDto;
        }
        return null;
    }

    @Override
    public PermissionDto permissionServiceDeleteById(Long id) {
        PermissionDto permissionDto = permissionServiceFindById(id);
        if(permissionDto != null){
            iPermissionRepository.deleteById(id);
            return permissionDto;
        }
        return null;
    }
}

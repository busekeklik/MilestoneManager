package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.AbsenceDto;
import com.etiya.milestonemanager.business.services.IAbsenceServices;
import com.etiya.milestonemanager.data.entity.AbsenceEntity;
import com.etiya.milestonemanager.data.repository.IAbsenceRepository;
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
public class AbsenceServicesImpl implements IAbsenceServices<AbsenceDto, AbsenceEntity> {
    private final ModelMapperBean modelMapperBean;
    private final IAbsenceRepository iAbsenceRepository;

    @Override
    public AbsenceDto entityToDto(AbsenceEntity absenceEntity) {
        return modelMapperBean.getModelMapperMethod().map(absenceEntity, AbsenceDto.class);
    }

    @Override
    public AbsenceEntity dtoToEntity(AbsenceDto absenceDto) {
        return modelMapperBean.getModelMapperMethod().map(absenceDto, AbsenceEntity.class);
    }

    @Override
    public void absenceServiceDeleteAllData() {
        iAbsenceRepository.deleteAll();

    }

    @Override
    @Transactional
    public AbsenceDto absenceServiceCreate(AbsenceDto absenceDto) {
        if(absenceDto != null){
            AbsenceEntity absenceEntity = dtoToEntity(absenceDto);
            absenceDto.setStartDate(absenceEntity.getStartDate());
            absenceDto.setEndDate(absenceEntity.getEndDate());
            absenceDto.setType(absenceDto.getType());
            absenceDto.setDescription(absenceEntity.getDescription());
            return absenceDto;
        }
        return null;
    }

    @Override
    public List<AbsenceDto> absenceServiceList() {
        Iterable<AbsenceEntity> absenceEntities = iAbsenceRepository.findAll();
        List<AbsenceDto> absenceDtoList = new ArrayList<>();
        for(AbsenceEntity e: absenceEntities){
            AbsenceDto absenceDto = entityToDto(e);
            absenceDtoList.add(absenceDto);
        }
        return absenceDtoList;
    }

    @Override
    public AbsenceDto absenceServiceFindById(Long id) {
        AbsenceEntity absenceEntity = null;
        if(id != null){
            absenceEntity = iAbsenceRepository.findById(id).
                    orElseThrow(()->new Auth404Exception(id + "nolu veri yoktur"));
        }
        else if(id == null){
            throw new GeneralException("task id null");
        }
        return entityToDto(absenceEntity);

    }

    @Override
    @Transactional
    public AbsenceDto absenceServiceUpdateById(Long id, AbsenceDto absenceDto) {
        AbsenceDto updateAbsenceDto = absenceServiceFindById(id);
        if(updateAbsenceDto != null){
            AbsenceEntity absenceEntity = dtoToEntity(updateAbsenceDto);
            absenceEntity.setStartDate(absenceDto.getStartDate());
            absenceEntity.setEndDate(absenceDto.getEndDate());
            absenceEntity.setType(absenceDto.getType());
            absenceEntity.setDescription(absenceDto.getDescription());
            iAbsenceRepository.save(absenceEntity);
            return updateAbsenceDto;
        }
        return null;
    }

    @Override
    public AbsenceDto absenceServiceDeleteById(Long id) {
        AbsenceDto absenceDto = absenceServiceFindById(id);
        if(absenceDto != null){
            iAbsenceRepository.deleteById(id);
            return absenceDto;
        }
        return null;
    }
}
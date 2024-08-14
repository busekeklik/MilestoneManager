package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.AbsenceDto;
import com.etiya.milestonemanager.business.services.IAbsenceServices;
import com.etiya.milestonemanager.data.entity.AbsenceEntity;
import com.etiya.milestonemanager.data.entity.UserEntity;
import com.etiya.milestonemanager.data.repository.IAbsenceRepository;
import com.etiya.milestonemanager.data.repository.IUserRepository;
import com.etiya.milestonemanager.exception.Auth404Exception;
import com.etiya.milestonemanager.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class AbsenceServicesImpl implements IAbsenceServices<AbsenceDto, AbsenceEntity> {

    private final ModelMapperBean modelMapperBean;
    private final IAbsenceRepository iAbsenceRepository;
    private final IUserRepository iUserRepository; // Inject the user repository

    @Override
    public AbsenceDto entityToDto(AbsenceEntity absenceEntity) {
        AbsenceDto absenceDto = modelMapperBean.getModelMapperMethod().map(absenceEntity, AbsenceDto.class);
        absenceDto.setUserId(absenceEntity.getUser().getUserID()); // Map userId from the entity
        return absenceDto;
    }

    @Override
    public AbsenceEntity dtoToEntity(AbsenceDto absenceDto) {
        AbsenceEntity absenceEntity = modelMapperBean.getModelMapperMethod().map(absenceDto, AbsenceEntity.class);

        // Find the user by ID and set it to the absence entity
        UserEntity userEntity = iUserRepository.findById(absenceDto.getUserId())
                .orElseThrow(() -> new Auth404Exception("User not found with ID: " + absenceDto.getUserId()));
        absenceEntity.setUser(userEntity);

        return absenceEntity;
    }

    @Override
    public void absenceServiceDeleteAllData() {
        iAbsenceRepository.deleteAll();
    }

    @Override
    @Transactional
    public AbsenceDto absenceServiceCreate(AbsenceDto absenceDto) {
        if (absenceDto != null) {
            AbsenceEntity absenceEntity = dtoToEntity(absenceDto);
            iAbsenceRepository.save(absenceEntity);
            return entityToDto(absenceEntity);
        }
        return null;
    }

    @Override
    public List<AbsenceDto> absenceServiceList() {
        Iterable<AbsenceEntity> absenceEntities = iAbsenceRepository.findAll();
        List<AbsenceDto> absenceDtoList = new ArrayList<>();
        for (AbsenceEntity e : absenceEntities) {
            AbsenceDto absenceDto = entityToDto(e);
            absenceDtoList.add(absenceDto);
        }
        return absenceDtoList;
    }

    @Override
    public AbsenceDto absenceServiceFindById(Long id) {
        AbsenceEntity absenceEntity = null;
        if (id != null) {
            absenceEntity = iAbsenceRepository.findById(id)
                    .orElseThrow(() -> new Auth404Exception(id + " bulunamadı!"));
        } else {
            throw new GeneralException("absence id null");
        }
        return entityToDto(absenceEntity);
    }

    @Override
    @Transactional
    public AbsenceDto absenceServiceUpdateById(Long id, AbsenceDto absenceDto) {
        AbsenceDto updateAbsenceDto = absenceServiceFindById(id);
        if (updateAbsenceDto != null) {
            AbsenceEntity absenceEntity = dtoToEntity(absenceDto);
            absenceEntity.setStartDate(absenceDto.getStartDate());
            absenceEntity.setEndDate(absenceDto.getEndDate());
            absenceEntity.setType(absenceDto.getType());
            absenceEntity.setDescription(absenceDto.getDescription());
            iAbsenceRepository.save(absenceEntity);
            return entityToDto(absenceEntity);
        }
        return null;
    }

    @Override
    @Transactional
    public AbsenceDto absenceServiceDeleteById(Long id) {
        AbsenceDto absenceDto = absenceServiceFindById(id);
        if (absenceDto != null) {
            iAbsenceRepository.deleteById(id);
            return absenceDto;
        }
        return null;
    }
}

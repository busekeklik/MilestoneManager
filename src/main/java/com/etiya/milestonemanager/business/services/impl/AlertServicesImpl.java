package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.AlertDto;
import com.etiya.milestonemanager.business.services.IAlertServices;
import com.etiya.milestonemanager.data.entity.AlertEntity;
import com.etiya.milestonemanager.data.repository.IAlertRepository;
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
public class AlertServicesImpl implements IAlertServices<AlertDto, AlertEntity> {

    private final ModelMapperBean modelMapperBean;
    private final IAlertRepository iAlertRepository;

    @Override
    public AlertDto entityToDto(AlertEntity alertEntity) {
        return modelMapperBean.getModelMapperMethod().map(alertEntity, AlertDto.class);
    }

    @Override
    public AlertEntity dtoToEntity(AlertDto alertDto) {
        return modelMapperBean.getModelMapperMethod().map(alertDto, AlertEntity.class);
    }

    @Override
    public void alertServiceDeleteAllData() {
        iAlertRepository.deleteAll();
    }

    @Override
    @Transactional
    public AlertDto alertServiceCreate(AlertDto alertDto) {
        if(alertDto != null){
            AlertEntity alertEntity = dtoToEntity(alertDto);
            alertDto.setTaskID(alertEntity.getTaskID());
            alertDto.setAlertDate(alertEntity.getAlertDate());
            alertDto.setMessage(alertEntity.getMessage());
            return alertDto;
        }
        return null;
    }

    @Override
    public List<AlertDto> alertServiceList() {
        Iterable<AlertEntity> alertEntities = iAlertRepository.findAll();
        List<AlertDto> alertDtoList = new ArrayList<>();
        for(AlertEntity e: alertEntities){
            AlertDto alertDto = entityToDto(e);
            alertDtoList.add(alertDto);
        }
        return alertDtoList;
    }

    @Override
    public AlertDto alertServiceFindById(Long id) {
        AlertEntity alertEntity = null;
        if(id != null){
            alertEntity = iAlertRepository.findById(id)
                    .orElseThrow(() -> new Auth404Exception(id + " bulunamadı!"));
        } else {
            throw new GeneralException("alert id null");
        }
        return entityToDto(alertEntity);
    }

    @Override
    @Transactional
    public AlertDto alertServiceUpdateById(Long id, AlertDto alertDto) {
        AlertDto updateAlertDto = alertServiceFindById(id);
        if(updateAlertDto != null){
            AlertEntity alertEntity = dtoToEntity(updateAlertDto);
            alertEntity.setTaskID(alertDto.getTaskID());
            alertEntity.setAlertDate(alertDto.getAlertDate());
            alertEntity.setMessage(alertDto.getMessage());
            iAlertRepository.save(alertEntity);
            return updateAlertDto;
        }
        return null;
    }

    @Override
    @Transactional
    public AlertDto alertServiceDeleteById(Long id) {
        AlertDto alertDto = alertServiceFindById(id);
        if(alertDto != null){
            iAlertRepository.deleteById(id);
            return alertDto;
        }
        return null;
    }
}

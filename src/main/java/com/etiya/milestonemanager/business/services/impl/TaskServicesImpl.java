package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.TaskDto;
import com.etiya.milestonemanager.business.services.ITaskServices;
import com.etiya.milestonemanager.data.entity.TaskEntity;
import com.etiya.milestonemanager.data.repository.ITaskRepository;
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
public class TaskServicesImpl implements ITaskServices<TaskDto, TaskEntity> {
    private final ModelMapperBean modelMapperBean;
    private final ITaskRepository iTaskRepository;

    @Override
    public TaskDto entityToDto(TaskEntity taskEntity) {
        return modelMapperBean.getModelMapperMethod().map(taskEntity, TaskDto.class);
    }

    @Override
    public TaskEntity dtoToEntity(TaskDto taskDto) {
        return modelMapperBean.getModelMapperMethod().map(taskDto, TaskEntity.class);
    }

    @Override
    public void taskServiceDeleteAllData() {
        iTaskRepository.deleteAll();
    }

    @Override
    @Transactional
    public TaskDto taskServiceCreate(TaskDto taskDto) {
        if (taskDto != null) {
            TaskEntity taskEntity = dtoToEntity(taskDto);
            TaskEntity savedTaskEntity = iTaskRepository.save(taskEntity);
            return entityToDto(savedTaskEntity);
        }
        return null;
    }

    @Override
    public List<TaskDto> taskServiceList() {
        Iterable<TaskEntity> taskEntities = iTaskRepository.findAll();
        List<TaskDto> taskDtoList = new ArrayList<>();
        for (TaskEntity e : taskEntities) {
            TaskDto taskDto = entityToDto(e);
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }

    @Override
    public TaskDto taskServiceFindById(Long id) {
        TaskEntity taskEntity = null;
        if (id != null) {
            taskEntity = iTaskRepository.findById(id)
                    .orElseThrow(() -> new Auth404Exception(id + " nolu veri yoktur."));
        } else if (id == null) {
            throw new GeneralException("task id null");
        }
        return entityToDto(taskEntity);
    }

    @Override
    @Transactional
    public TaskDto taskServiceUpdateById(Long id, TaskDto taskDto) {
        TaskDto updateTaskDto = taskServiceFindById(id);
        if (updateTaskDto != null) {
            TaskEntity taskEntity = dtoToEntity(updateTaskDto);
            taskEntity.setTaskName(taskDto.getTaskName());
            taskEntity.setStartDate(taskDto.getStartDate());
            taskEntity.setEndDate(taskDto.getEndDate());
            taskEntity.setManDays(taskDto.getManDays());
            taskEntity.setCost(taskDto.getCost());
            taskEntity.setSeverity(taskDto.getSeverity());
            taskEntity.setProgress(taskDto.getProgress());
            TaskEntity updatedTaskEntity = iTaskRepository.save(taskEntity);
            return entityToDto(updatedTaskEntity);
        }
        return null;
    }

    @Override
    @Transactional
    public TaskDto taskServiceDeleteById(Long id) {
        TaskDto taskDto = taskServiceFindById(id);
        if (taskDto != null) {
            iTaskRepository.deleteById(id);
            return taskDto;
        }
        return null;
    }
}

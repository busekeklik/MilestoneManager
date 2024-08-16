package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.bean.ModelMapperBean;
import com.etiya.milestonemanager.business.dto.TaskDto;
import com.etiya.milestonemanager.business.services.ITaskServices;
import com.etiya.milestonemanager.data.entity.TaskEntity;
import com.etiya.milestonemanager.data.entity.UserEntity;
import com.etiya.milestonemanager.data.repository.ITaskRepository;
import com.etiya.milestonemanager.data.repository.IUserRepository;
import com.etiya.milestonemanager.exception.Auth404Exception;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServicesImpl implements ITaskServices<TaskDto, TaskEntity> {

    private final ModelMapperBean modelMapperBean;
    private final ITaskRepository iTaskRepository;
    private final IUserRepository iUserRepository;

    @Override
    public TaskDto entityToDto(TaskEntity taskEntity) {
        TaskDto taskDto = modelMapperBean.getModelMapperMethod().map(taskEntity, TaskDto.class);
        taskDto.setAnalystIds(taskEntity.getAnalysts().stream().map(UserEntity::getUserID).collect(Collectors.toList()));
        taskDto.setSolutionArchitectIds(taskEntity.getSolutionArchitects().stream().map(UserEntity::getUserID).collect(Collectors.toList()));
        taskDto.setSoftwareArchitectIds(taskEntity.getSoftwareArchitects().stream().map(UserEntity::getUserID).collect(Collectors.toList()));
        taskDto.setDependencyIds(taskEntity.getDependencies().stream().map(TaskEntity::getTaskID).collect(Collectors.toList()));
        return taskDto;
    }

    @Override
    public TaskEntity dtoToEntity(TaskDto taskDto) {
        TaskEntity taskEntity = modelMapperBean.getModelMapperMethod().map(taskDto, TaskEntity.class);
        return taskEntity;
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

            // Ensure non-null lists
            List<UserEntity> analysts = taskDto.getAnalystIds() != null
                    ? iUserRepository.findAllById(taskDto.getAnalystIds())
                    : new ArrayList<>();

            List<UserEntity> solutionArchitects = taskDto.getSolutionArchitectIds() != null
                    ? iUserRepository.findAllById(taskDto.getSolutionArchitectIds())
                    : new ArrayList<>();

            List<UserEntity> softwareArchitects = taskDto.getSoftwareArchitectIds() != null
                    ? iUserRepository.findAllById(taskDto.getSoftwareArchitectIds())
                    : new ArrayList<>();

            taskEntity.setAnalysts(analysts);
            taskEntity.setSolutionArchitects(solutionArchitects);
            taskEntity.setSoftwareArchitects(softwareArchitects);

            // Handle task dependencies
            List<TaskEntity> dependencies = new ArrayList<>();
            if (taskDto.getDependencyIds() != null) {
                dependencies = StreamSupport.stream(iTaskRepository.findAllById(taskDto.getDependencyIds()).spliterator(), false)
                        .collect(Collectors.toList());
            }
            taskEntity.setDependencies(dependencies);

            TaskEntity savedTaskEntity = iTaskRepository.save(taskEntity);
            return entityToDto(savedTaskEntity);
        }
        return null;
    }


    @Override
    @Transactional
    public TaskDto taskServiceUpdateById(Long id, TaskDto taskDto) {
        TaskDto updateTaskDto = taskServiceFindById(id);
        if (updateTaskDto != null) {
            TaskEntity taskEntity = dtoToEntity(updateTaskDto);

            if (taskDto.getTaskName() != null) {
                taskEntity.setTaskName(taskDto.getTaskName());
            }
            if (taskDto.getStartDate() != null) {
                taskEntity.setStartDate(taskDto.getStartDate());
            }
            if (taskDto.getEndDate() != null) {
                taskEntity.setEndDate(taskDto.getEndDate());
            }

            taskEntity.setManDays(taskDto.getManDays());
            taskEntity.setCost(taskDto.getCost());
            taskEntity.setSeverity(taskDto.getSeverity());
            taskEntity.setProgress(taskDto.getProgress());

            // Set the deleted flag if provided
            taskEntity.setDeleted(taskDto.isDeleted());

            List<UserEntity> analysts = taskDto.getAnalystIds() != null
                    ? iUserRepository.findAllById(taskDto.getAnalystIds())
                    : new ArrayList<>();

            List<UserEntity> solutionArchitects = taskDto.getSolutionArchitectIds() != null
                    ? iUserRepository.findAllById(taskDto.getSolutionArchitectIds())
                    : new ArrayList<>();

            List<UserEntity> softwareArchitects = taskDto.getSoftwareArchitectIds() != null
                    ? iUserRepository.findAllById(taskDto.getSoftwareArchitectIds())
                    : new ArrayList<>();

            taskEntity.setAnalysts(analysts);
            taskEntity.setSolutionArchitects(solutionArchitects);
            taskEntity.setSoftwareArchitects(softwareArchitects);

            List<TaskEntity> dependencies = taskDto.getDependencyIds() != null
                    ? StreamSupport.stream(iTaskRepository.findAllById(taskDto.getDependencyIds()).spliterator(), false)
                    .collect(Collectors.toList())
                    : new ArrayList<>();
            taskEntity.setDependencies(dependencies);

            TaskEntity updatedTaskEntity = iTaskRepository.save(taskEntity);
            return entityToDto(updatedTaskEntity);
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
        TaskEntity taskEntity = iTaskRepository.findById(id)
                .orElseThrow(() -> new Auth404Exception(id + " nolu veri yoktur."));
        return entityToDto(taskEntity);
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

    @Override
    public List<TaskDto> taskServiceListByProjectId(Long projectId) {
        List<TaskEntity> taskEntities = iTaskRepository.findByProject_ProjectIdAndDeletedFalse(projectId);
        List<TaskDto> taskDtoList = new ArrayList<>();
        for (TaskEntity taskEntity : taskEntities) {
            TaskDto taskDto = entityToDto(taskEntity);
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }
}
package com.etiya.milestonemanager.business.services.impl;

import com.etiya.milestonemanager.business.dto.UserTaskDto;
import com.etiya.milestonemanager.business.services.IUserTaskServices;
import com.etiya.milestonemanager.data.entity.UserTaskEntity;
import com.etiya.milestonemanager.data.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTaskServicesImpl implements IUserTaskServices {
    private final UserTaskRepository userTaskRepository;

    @Override
    public void userTaskServiceDeleteAllData() {
        userTaskRepository.deleteAll();
    }

    @Override
    public UserTaskDto userTaskServiceCreate(UserTaskDto userTaskDto) {
        UserTaskEntity userTaskEntity = new UserTaskEntity();
        userTaskEntity.setUserID(userTaskDto.getUserID());
        userTaskEntity.setTaskID(userTaskDto.getTaskID());
        userTaskRepository.save(userTaskEntity);
        return userTaskDto;
    }

    @Override
    public List<UserTaskDto> userTaskServiceList() {
        return userTaskRepository.findAll().stream()
                .map(entity -> {
                    UserTaskDto dto = new UserTaskDto();
                    dto.setUserID(entity.getUserID());
                    dto.setTaskID(entity.getTaskID());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserTaskDto userTaskServiceFindById(Long id) {
        Optional<UserTaskEntity> userTaskEntity = userTaskRepository.findById(id);
        if (userTaskEntity.isPresent()) {
            UserTaskDto dto = new UserTaskDto();
            dto.setUserID(userTaskEntity.get().getUserID());
            dto.setTaskID(userTaskEntity.get().getTaskID());
            return dto;
        }
        return null;
    }

    @Override
    public UserTaskDto userTaskServiceUpdateById(Long id, UserTaskDto userTaskDto) {
        Optional<UserTaskEntity> userTaskEntityOptional = userTaskRepository.findById(id);
        if (userTaskEntityOptional.isPresent()) {
            UserTaskEntity userTaskEntity = userTaskEntityOptional.get();
            userTaskEntity.setUserID(userTaskDto.getUserID());
            userTaskEntity.setTaskID(userTaskDto.getTaskID());
            userTaskRepository.save(userTaskEntity);
            return userTaskDto;
        }
        return null;
    }

    @Override
    public void userTaskServiceDeleteById(Long id) {
        userTaskRepository.deleteById(id);
    }
}

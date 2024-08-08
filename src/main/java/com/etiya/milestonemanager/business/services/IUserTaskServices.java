package com.etiya.milestonemanager.business.services;

import com.etiya.milestonemanager.business.dto.UserTaskDto;

import java.util.List;

public interface IUserTaskServices {
    void userTaskServiceDeleteAllData();
    UserTaskDto userTaskServiceCreate(UserTaskDto userTaskDto);
    List<UserTaskDto> userTaskServiceList();
    UserTaskDto userTaskServiceFindById(Long id);
    UserTaskDto userTaskServiceUpdateById(Long id, UserTaskDto userTaskDto);
    void userTaskServiceDeleteById(Long id);
}

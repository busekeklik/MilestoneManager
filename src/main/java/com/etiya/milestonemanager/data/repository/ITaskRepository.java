package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends CrudRepository<TaskEntity, Long> {
    List<TaskEntity> findByProject_ProjectIdAndDeletedFalse(Long projectId);
}

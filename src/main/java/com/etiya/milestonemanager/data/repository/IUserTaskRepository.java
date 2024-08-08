package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserTaskRepository extends JpaRepository<UserTaskEntity, Long> {
}

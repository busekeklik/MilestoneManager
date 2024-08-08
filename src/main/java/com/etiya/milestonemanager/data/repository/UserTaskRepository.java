package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {
}

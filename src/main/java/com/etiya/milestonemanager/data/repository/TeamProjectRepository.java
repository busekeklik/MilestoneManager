package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.TeamProjectEntity;
import com.etiya.milestonemanager.data.entity.TeamProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamProjectRepository extends JpaRepository<TeamProjectEntity, TeamProjectId> {
    List<TeamProjectEntity> findByProjectID(Long projectID);
}

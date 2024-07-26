package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectRepository extends CrudRepository<ProjectEntity, Long> {

}

package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.PermissionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionRepository extends CrudRepository<PermissionEntity, Long> {
}

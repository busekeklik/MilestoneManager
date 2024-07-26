package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends CrudRepository<RoleEntity, Long> {

}

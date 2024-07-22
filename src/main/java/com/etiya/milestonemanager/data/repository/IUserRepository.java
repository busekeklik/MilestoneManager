package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<UserEntity, Long> {
}

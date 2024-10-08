package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.RoleType;
import com.etiya.milestonemanager.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findFirstByEmail(String email);
    List<UserEntity> findByRolesContaining(RoleType role);

}

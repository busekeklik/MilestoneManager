package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.AbsenceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAbsenceRepository extends CrudRepository<AbsenceEntity, Long> {
}

package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.AlertEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAlertRepository extends CrudRepository<AlertEntity, Long> {
}

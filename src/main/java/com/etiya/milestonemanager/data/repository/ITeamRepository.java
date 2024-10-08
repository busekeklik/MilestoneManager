package com.etiya.milestonemanager.data.repository;

import com.etiya.milestonemanager.data.entity.TeamEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamRepository extends CrudRepository<TeamEntity, Long> {
}

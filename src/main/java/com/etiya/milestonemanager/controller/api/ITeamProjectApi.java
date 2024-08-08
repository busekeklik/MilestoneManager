package com.etiya.milestonemanager.controller.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITeamProjectApi<D> {
    ResponseEntity<String> teamProjectApiAllDelete();
    ResponseEntity<?> teamProjectApiCreate(D d);
    ResponseEntity<List<D>> teamProjectApiList();
    ResponseEntity<?> teamProjectApiFindById(Long teamID, Long projectID);
    ResponseEntity<?> teamProjectApiUpdate(Long teamID, Long projectID, D d);
    ResponseEntity<?> teamProjectApiDeleteById(Long teamID, Long projectID);
    ResponseEntity<List<D>> teamProjectApiFindByProjectId(Long projectId);
}

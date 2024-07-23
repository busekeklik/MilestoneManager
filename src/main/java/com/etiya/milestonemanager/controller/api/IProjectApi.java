package com.etiya.milestonemanager.controller.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProjectApi<D> {

    // ALL DELETE
    public ResponseEntity<String> projectApiAllDelete();

    // C R U D
    // CREATE
    public ResponseEntity<?> projectApiCreate(D d);

    // LIST
    public ResponseEntity<List<D>> projectApiList();

    // FIND BY
    public ResponseEntity<?> projectApiFindById(Long id);

    // UPDATE
    public ResponseEntity<?> projectApiUpdate(Long id, D d);

    // DELETE
    public ResponseEntity<?> projectApiDeleteById(Long id);

}

package com.etiya.milestonemanager.controller.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRoleApi<D> {

    // ALL DELETE
    public ResponseEntity<String> roleApiAllDelete();


    // C R U D
    // CREATE
    public ResponseEntity<?>  roleApiCreate(D d);

    // LIST
    public ResponseEntity<List<D>>  roleApiList();

    // FIND BY
    public ResponseEntity<?>  roleApiFindById(Long id);

    // UPDATE
    public ResponseEntity<?>  roleApiUpdate(Long id,D d);

    // DELETE
    public ResponseEntity<?>  roleApiDeleteById(Long id);
}

package com.etiya.milestonemanager.controller.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskApi<D> {
    // ALL DELETE
    public ResponseEntity<String> taskApiAllDelete();


    // C R U D
    // CREATE
    public ResponseEntity<?>  taskApiCreate(D d);

    // LIST
    public ResponseEntity<List<D>>  taskApiList();

    // FIND BY
    public ResponseEntity<?>  taskApiFindById(Long id);

    // UPDATE
    public ResponseEntity<?>  taskApiUpdate(Long id,D d);

    // DELETE
    public ResponseEntity<?>  taskApiDeleteById(Long id);
}

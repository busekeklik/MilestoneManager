package com.etiya.milestonemanager.controller.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITeamApi<D>{
    // ALL DELETE
    public ResponseEntity<String> teamApiAllDelete();


    // C R U D
    // CREATE
    public ResponseEntity<?>  teamApiCreate(D d);

    // LIST
    public ResponseEntity<List<D>>  teamApiList();

    // FIND BY
    public ResponseEntity<?>  teamApiFindById(Long id);

    // UPDATE
    public ResponseEntity<?>  teamApiUpdate(Long id,D d);

    // DELETE
    public ResponseEntity<?> teamApiDeleteById(Long id);
}

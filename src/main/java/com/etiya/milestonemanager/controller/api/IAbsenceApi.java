package com.etiya.milestonemanager.controller.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAbsenceApi<D> {

    // ALL DELETE
    public ResponseEntity<String> absenceApiAllDelete();


    // C R U D
    // CREATE
    public ResponseEntity<?>  absenceApiCreate(D d);

    // LIST
    public ResponseEntity<List<D>>  absenceApiList();

    // FIND BY
    public ResponseEntity<?>  absenceApiFindById(Long id);

    // UPDATE
    public ResponseEntity<?>  absenceApiUpdate(Long id,D d);

    // DELETE
    public ResponseEntity<?>  absenceApiDeleteById(Long id);

}

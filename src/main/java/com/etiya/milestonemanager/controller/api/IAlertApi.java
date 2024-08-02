package com.etiya.milestonemanager.controller.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAlertApi<D> {

    // CREATE
    ResponseEntity<?> alertApiCreate(D d);

    // LIST
    ResponseEntity<List<D>> alertApiList();

    // FIND BY ID
    ResponseEntity<?> alertApiFindById(Long id);

    // UPDATE
    ResponseEntity<?> alertApiUpdate(Long id, D d);

    // DELETE
    ResponseEntity<?> alertApiDeleteById(Long id);
}
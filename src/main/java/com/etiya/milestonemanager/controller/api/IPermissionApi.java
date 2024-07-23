package com.etiya.milestonemanager.controller.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPermissionApi<D> {

    // ALL DELETE
    public ResponseEntity<String> permissionApiAllDelete();


    // C R U D
    // CREATE
    public ResponseEntity<?>  permissionApiCreate(D d);

    // LIST
    public ResponseEntity<List<D>>  permissionApiList();

    // FIND BY
    public ResponseEntity<?>  permissionApiFindById(Long id);

    // UPDATE
    public ResponseEntity<?>  permissionApiUpdate(Long id,D d);

    // DELETE
    public ResponseEntity<?>  permissionApiDeleteById(Long id);
}

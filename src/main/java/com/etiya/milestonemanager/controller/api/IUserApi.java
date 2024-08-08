package com.etiya.milestonemanager.controller.api;

import com.etiya.milestonemanager.data.entity.RoleType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserApi<D> {

    // ALL DELETE
    public ResponseEntity<String> userApiAllDelete();


    // C R U D
    // CREATE
    public ResponseEntity<?>  userApiCreate(D d);

    // LIST
    public ResponseEntity<List<D>>  userApiList();

    // FIND BY
    public ResponseEntity<?>  userApiFindById(Long id);

    // UPDATE
    public ResponseEntity<?>  userApiUpdate(Long id,D d);

    // DELETE
    public ResponseEntity<?>  userApiDeleteById(Long id);

    ResponseEntity<List<D>> userApiFindByRole(String role);
}

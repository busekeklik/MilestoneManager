package com.etiya.milestonemanager.controller.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserTaskApi<D> {
    ResponseEntity<String> userTaskApiAllDelete();
    ResponseEntity<?> userTaskApiCreate(D d);
    ResponseEntity<List<D>> userTaskApiList();
    ResponseEntity<?> userTaskApiFindById(Long id);
    ResponseEntity<?> userTaskApiUpdate(Long id, D d);
    ResponseEntity<?> userTaskApiDeleteById(Long id);
}

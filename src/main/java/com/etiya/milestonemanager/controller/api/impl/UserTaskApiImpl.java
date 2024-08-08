package com.etiya.milestonemanager.controller.api.impl;

import com.etiya.milestonemanager.business.dto.UserTaskDto;
import com.etiya.milestonemanager.business.services.IUserTaskServices;
import com.etiya.milestonemanager.controller.api.IUserTaskApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// LOMBOK
@RequiredArgsConstructor
@Log4j2

// API
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user_task/api/v1")
public class UserTaskApiImpl implements IUserTaskApi<UserTaskDto> {

    private final IUserTaskServices iUserTaskServices;

    @Override
    @DeleteMapping(value = "/delete/all")
    public ResponseEntity<String> userTaskApiAllDelete() {
        iUserTaskServices.userTaskServiceDeleteAllData();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> userTaskApiCreate(@Valid @RequestBody UserTaskDto userTaskDto) {
        return ResponseEntity.ok(iUserTaskServices.userTaskServiceCreate(userTaskDto));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<UserTaskDto>> userTaskApiList() {
        return ResponseEntity.status(HttpStatus.OK).body(iUserTaskServices.userTaskServiceList());
    }

    @Override
    @GetMapping(value = "/find/{id}")
    public ResponseEntity<?> userTaskApiFindById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(200).body(iUserTaskServices.userTaskServiceFindById(id));
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> userTaskApiUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody UserTaskDto userTaskDto) {
        return ResponseEntity.ok().body(iUserTaskServices.userTaskServiceUpdateById(id, userTaskDto));
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> userTaskApiDeleteById(@PathVariable(name = "id") Long id) {
        iUserTaskServices.userTaskServiceDeleteById(id);
        return ResponseEntity.ok().build();
    }
}

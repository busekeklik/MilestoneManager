package com.etiya.milestonemanager.controller.api.impl;

import com.etiya.milestonemanager.business.dto.TaskDto;
import com.etiya.milestonemanager.business.services.ITaskServices;
import com.etiya.milestonemanager.controller.api.ITaskApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/task/api/v1")
public class TaskApiImpl implements ITaskApi<TaskDto> {

    private final ITaskServices iTaskServices;

    @Override
    @DeleteMapping(value = "/delete/all")
    public ResponseEntity<String> taskApiAllDelete() {
        iTaskServices.taskServiceDeleteAllData();
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/list/project/{projectId}")
    public ResponseEntity<List<TaskDto>> taskApiListByProjectId(@PathVariable(name = "projectId") Long projectId) {
        List<TaskDto> tasks = iTaskServices.taskServiceListByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<?> taskApiCreate(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(iTaskServices.taskServiceCreate(taskDto));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<TaskDto>> taskApiList() {
        return ResponseEntity.status(HttpStatus.OK).body(iTaskServices.taskServiceList());
    }

    @Override
    @GetMapping(value = "/find/{id}")
    public ResponseEntity<?> taskApiFindById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(200).body(iTaskServices.taskServiceFindById(id));
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> taskApiUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok().body(iTaskServices.taskServiceUpdateById(id, taskDto));
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> taskApiDeleteById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(iTaskServices.taskServiceDeleteById(id), HttpStatus.OK);
    }
}

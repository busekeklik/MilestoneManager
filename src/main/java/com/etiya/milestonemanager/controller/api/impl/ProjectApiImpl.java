package com.etiya.milestonemanager.controller.api.impl;

import com.etiya.milestonemanager.business.dto.ProjectDto;
import com.etiya.milestonemanager.business.services.IProjectServices;
import com.etiya.milestonemanager.controller.api.IProjectApi;
import com.etiya.milestonemanager.data.entity.ProjectEntity;
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
@RequestMapping("/project/api/v1/projects")
public class ProjectApiImpl implements IProjectApi<ProjectDto> {

    private final IProjectServices<ProjectDto, ProjectEntity> iProjectServices;

    @Override
    @DeleteMapping(value="/delete/all")
    public ResponseEntity<String> projectApiAllDelete() {
        iProjectServices.projectServiceDeleteAllData();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> projectApiCreate(@RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(iProjectServices.projectServiceCreate(projectDto));
    }

    @Override
    @GetMapping(value="/list")
    public ResponseEntity<List<ProjectDto>> projectApiList() {
        return ResponseEntity.status(HttpStatus.OK).body(iProjectServices.projectServiceList());
    }

    @Override
    @GetMapping(value="/find/{id}")
    public ResponseEntity<?> projectApiFindById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(iProjectServices.projectServiceFindById(id));
    }

    @Override
    @PutMapping(value="/update/{id}")
    public ResponseEntity<?> projectApiUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok().body(iProjectServices.projectServiceUpdateById(id, projectDto));
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> projectApiDeleteById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(iProjectServices.projectServiceDeleteById(id), HttpStatus.OK);
    }
}

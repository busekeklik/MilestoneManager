package com.etiya.milestonemanager.controller.api.impl;

import com.etiya.milestonemanager.business.dto.PermissionDto;
import com.etiya.milestonemanager.business.services.IPermissionServices;
import com.etiya.milestonemanager.controller.api.IPermissionApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// LOMBOK
@RequiredArgsConstructor
@Log4j2

@RestController
@CrossOrigin(origins = " http://localhost:3000")
@RequestMapping("/permission/api/v1")
public class PermissionImpl implements IPermissionApi<PermissionDto> {

    private final IPermissionServices iPermissionServices;

    @Override
    @DeleteMapping(value="/delete/all")
    public ResponseEntity<String> permissionApiAllDelete() {
        iPermissionServices.permissionServiceDeleteAllData();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> permissionApiCreate(PermissionDto permissionDto) {
        return ResponseEntity.ok(iPermissionServices.permissionServiceCreate(permissionDto));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<PermissionDto>> permissionApiList() {
        return ResponseEntity.status(HttpStatus.OK).body(iPermissionServices.permissionServiceList());
    }

    @Override
    @GetMapping(value="/find/{id}")
    public ResponseEntity<?> permissionApiFindById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(200).body(iPermissionServices.permissionServiceFindById(id));
    }

    @Override
    @PutMapping(value="/update/{id}")
    public ResponseEntity<?> permissionApiUpdate(Long id, PermissionDto permissionDto) {
        return ResponseEntity.ok().body(iPermissionServices.permissionServiceUpdateById(id, permissionDto));
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> permissionApiDeleteById(Long id) {
        return new ResponseEntity<>(iPermissionServices.permissionServiceDeleteById(id), HttpStatus.OK);
    }
}

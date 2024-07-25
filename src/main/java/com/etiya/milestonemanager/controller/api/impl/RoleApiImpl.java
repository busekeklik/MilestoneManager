package com.etiya.milestonemanager.controller.api.impl;

import com.etiya.milestonemanager.business.dto.RoleDto;
import com.etiya.milestonemanager.business.services.IRoleServices;
import com.etiya.milestonemanager.controller.api.IRoleApi;
import com.etiya.milestonemanager.data.entity.RoleEntity;
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
@RequestMapping("/role/api/v1")
public class RoleApiImpl implements IRoleApi<RoleDto> {

    private final IRoleServices<RoleDto, RoleEntity> iRoleServices;

    @Override
    @DeleteMapping(value="/delete/all")

    public ResponseEntity<String> roleApiAllDelete() {
        iRoleServices.roleServiceDeleteAllData();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> roleApiCreate(RoleDto roleDto) {
        return ResponseEntity.ok(iRoleServices.roleServiceCreate(roleDto));
    }

    @Override
    @GetMapping(value="/list")
    public ResponseEntity<List<RoleDto>> roleApiList() {
        return ResponseEntity.status(HttpStatus.OK).body(iRoleServices.roleServiceList());
    }

    @Override
    @GetMapping(value="/find/{id}")
    public ResponseEntity<?> roleApiFindById(Long id) {
        return ResponseEntity.status(200).body(iRoleServices.roleServiceFindById(id));
    }

    @Override
    @PutMapping(value="/update/{id}")
    public ResponseEntity<?> roleApiUpdate(Long id, RoleDto roleDto) {
        return ResponseEntity.ok().body(iRoleServices.roleServiceUpdateById(id, roleDto));
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> roleApiDeleteById(Long id) {
        return new ResponseEntity<>(iRoleServices.roleServiceDeleteById(id), HttpStatus.OK);
    }
}

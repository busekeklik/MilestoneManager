package com.etiya.milestonemanager.controller.api.impl;

import com.etiya.milestonemanager.business.dto.UserDto;
import com.etiya.milestonemanager.business.services.IUserServices;
import com.etiya.milestonemanager.controller.api.IUserApi;
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
@CrossOrigin(origins = " http://localhost:3000")
@RequestMapping("/user/api/v1")
public class UserApiImpl implements IUserApi<UserDto> {

    private final IUserServices iUserServices;
    @Override
    @DeleteMapping(value="/delete/all")
    public ResponseEntity<String> userApiAllDelete() {
        iUserServices.userServiceDeleteAllData();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> userApiCreate(UserDto userDto) {
        return ResponseEntity.ok(iUserServices.userServiceCreate(userDto));
    }

    @Override
    @GetMapping(value="/list")
    public ResponseEntity<List<UserDto>> userApiList() {
        return ResponseEntity.status(HttpStatus.OK).body(iUserServices.userServiceList());
    }

    @Override
    @GetMapping(value="/find/{id}")
    public ResponseEntity<?> userApiFindById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(200).body(iUserServices.userServiceFindById(id));
    }

    @Override
    @PutMapping(value="/update/{id}")
    public ResponseEntity<?> userApiUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody  UserDto userDto) {
        return ResponseEntity.ok().body(iUserServices.userServiceUpdateById(id, userDto));
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> userApiDeleteById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(iUserServices.userServiceDeleteById(id),HttpStatus.OK);
    }
}

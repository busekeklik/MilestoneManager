package com.etiya.milestonemanager.controller.api.impl;

import com.etiya.milestonemanager.business.dto.AbsenceDto;
import com.etiya.milestonemanager.business.services.IAbsenceServices;
import com.etiya.milestonemanager.controller.api.IAbsenceApi;
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
@RequestMapping("/absence/api/v1")
public class AbsenceImpl implements IAbsenceApi<AbsenceDto> {

    private final IAbsenceServices iAbsenceServices;
    @Override
    @DeleteMapping(value = "delete/all")
    public ResponseEntity<String> absenceApiAllDelete() {
        iAbsenceServices.absenceServiceDeleteAllData();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> absenceApiCreate(AbsenceDto absenceDto) {
        return ResponseEntity.ok(iAbsenceServices.absenceServiceCreate(absenceDto));
    }

    @Override
    @GetMapping(value ="/list")
    public ResponseEntity<List<AbsenceDto>> absenceApiList() {
        return ResponseEntity.status(HttpStatus.OK).body(iAbsenceServices.absenceServiceList());
    }

    @Override
    @GetMapping(value="/find/{id}")
    public ResponseEntity<?> absenceApiFindById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(200).body(iAbsenceServices.absenceServiceFindById(id));
    }

    @Override
    @PutMapping(value="/update/{id}")
    public ResponseEntity<?> absenceApiUpdate(Long id, AbsenceDto absenceDto) {
        return ResponseEntity.ok().body(iAbsenceServices.absenceServiceUpdateById(id, absenceDto));
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> absenceApiDeleteById(Long id) {
        return new ResponseEntity<>(iAbsenceServices.absenceServiceDeleteById(id), HttpStatus.OK);
    }
}

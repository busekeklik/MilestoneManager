package com.etiya.milestonemanager.controller.api.impl;

import com.etiya.milestonemanager.business.dto.AlertDto;
import com.etiya.milestonemanager.business.services.IAlertServices;
import com.etiya.milestonemanager.controller.api.IAlertApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/alert/api/v1")
public class AlertApiImpl implements IAlertApi<AlertDto> {

    @Autowired
    private IAlertServices<AlertDto, ?> alertServices;

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> alertApiCreate(@RequestBody AlertDto alertDto) {
        AlertDto createdAlert = alertServices.alertServiceCreate(alertDto);
        return new ResponseEntity<>(createdAlert, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<List<AlertDto>> alertApiList() {
        List<AlertDto> alerts = alertServices.alertServiceList();
        return new ResponseEntity<>(alerts, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> alertApiFindById(@PathVariable Long id) {
        AlertDto alertDto = alertServices.alertServiceFindById(id);
        if (alertDto == null) {
            return new ResponseEntity<>("Alert not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(alertDto, HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> alertApiUpdate(@PathVariable Long id, @RequestBody AlertDto alertDto) {
        AlertDto updatedAlert = alertServices.alertServiceUpdateById(id, alertDto);
        if (updatedAlert == null) {
            return new ResponseEntity<>("Failed to update alert", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedAlert, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> alertApiDeleteById(@PathVariable Long id) {
        AlertDto alertDto = alertServices.alertServiceDeleteById(id);
        if (alertDto == null) {
            return new ResponseEntity<>("Failed to delete or alert not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Alert deleted successfully", HttpStatus.OK);
    }
}
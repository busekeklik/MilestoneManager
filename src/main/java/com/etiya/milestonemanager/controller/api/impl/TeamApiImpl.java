package com.etiya.milestonemanager.controller.api.impl;

import com.etiya.milestonemanager.business.dto.TeamDto;
import com.etiya.milestonemanager.business.services.ITeamServices;
import com.etiya.milestonemanager.controller.api.ITeamApi;
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

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/team/api/v1")
public class TeamApiImpl implements ITeamApi<TeamDto> {

    private final ITeamServices iTeamServices;

    @Override
    @DeleteMapping(value = "/delete/all")
    public ResponseEntity<String> teamApiAllDelete() {
        iTeamServices.teamServiceDeleteAllData();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> teamApiCreate(@Valid @RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(iTeamServices.teamServiceCreate(teamDto));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<TeamDto>> teamApiList() {
        return ResponseEntity.status(HttpStatus.OK).body(iTeamServices.teamServiceList());
    }

    @Override
    @GetMapping(value = "/find/{id}")
    public ResponseEntity<?> teamApiFindById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(iTeamServices.teamServiceFindById(id));
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> teamApiUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody TeamDto teamDto) {
        return ResponseEntity.ok().body(iTeamServices.teamServiceUpdateById(id, teamDto));
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> teamApiDeleteById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(iTeamServices.teamServiceDeleteById(id), HttpStatus.OK);
    }
}

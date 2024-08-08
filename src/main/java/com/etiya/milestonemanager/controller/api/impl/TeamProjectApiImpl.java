package com.etiya.milestonemanager.controller.api.impl;

import com.etiya.milestonemanager.business.dto.TeamProjectDto;
import com.etiya.milestonemanager.business.services.ITeamProjectServices;
import com.etiya.milestonemanager.controller.api.ITeamProjectApi;
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
@RequestMapping("/team_project/api/v1")
public class TeamProjectApiImpl implements ITeamProjectApi<TeamProjectDto> {

    private final ITeamProjectServices iTeamProjectServices;

    @Override
    @DeleteMapping(value = "/delete/all")
    public ResponseEntity<String> teamProjectApiAllDelete() {
        iTeamProjectServices.teamProjectServiceDeleteAllData();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> teamProjectApiCreate(@Valid @RequestBody TeamProjectDto teamProjectDto) {
        return ResponseEntity.ok(iTeamProjectServices.teamProjectServiceCreate(teamProjectDto));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<TeamProjectDto>> teamProjectApiList() {
        return ResponseEntity.status(HttpStatus.OK).body(iTeamProjectServices.teamProjectServiceList());
    }

    @Override
    @GetMapping(value = "/find")
    public ResponseEntity<?> teamProjectApiFindById(@RequestParam Long teamID, @RequestParam Long projectID) {
        return ResponseEntity.status(200).body(iTeamProjectServices.teamProjectServiceFindById(teamID, projectID));
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<?> teamProjectApiUpdate(@RequestParam Long teamID, @RequestParam Long projectID, @Valid @RequestBody TeamProjectDto teamProjectDto) {
        return ResponseEntity.ok().body(iTeamProjectServices.teamProjectServiceUpdateById(teamID, projectID, teamProjectDto));
    }

    @Override
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> teamProjectApiDeleteById(@RequestParam Long teamID, @RequestParam Long projectID) {
        iTeamProjectServices.teamProjectServiceDeleteById(teamID, projectID);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping(value = "/project/{projectId}")
    public ResponseEntity<List<TeamProjectDto>> teamProjectApiFindByProjectId(@PathVariable(name = "projectId") Long projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(iTeamProjectServices.teamProjectServiceFindByProjectId(projectId));
    }
}

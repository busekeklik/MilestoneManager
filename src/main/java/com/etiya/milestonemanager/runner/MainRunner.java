package com.etiya.milestonemanager.runner;

import com.etiya.milestonemanager.data.entity.*;
import com.etiya.milestonemanager.data.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Configuration
@Log4j2
public class MainRunner {

    private final IUserRepository iUserRepository;
    private final IProjectRepository iProjectRepository;
    private final ITeamRepository iTeamRepository;
    private final IAbsenceRepository iAbsenceRepository;
    private final IAlertRepository iAlertRepository;
    private final IPermissionRepository iPermissionRepository;
    private final ITaskRepository iTaskRepository;

    public void start() {
        log.info("###START#######");
    }

    @Bean
    public CommandLineRunner milestone() {
        return args -> {
            log.info("projects");
            System.out.println("users");

            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, 2024);
            calendar.set(Calendar.MONTH, Calendar.JULY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date startDate = calendar.getTime();
            calendar.set(Calendar.DAY_OF_MONTH, 30);
            Date endDate = calendar.getTime();

            ProjectEntity project1 = new ProjectEntity();
            project1.setProjectName("Backend");
            project1.setStartDate(startDate);
            project1.setEndDate(endDate);
            project1.setStatus("in progress");

            ProjectEntity project2 = new ProjectEntity();
            project2.setProjectName("Frontend");
            project2.setStartDate(startDate);
            project2.setEndDate(endDate);
            project2.setStatus("completed");

            ProjectEntity project3 = new ProjectEntity();
            project3.setProjectName("Mobile App");
            project3.setStartDate(startDate);
            project3.setEndDate(endDate);
            project3.setStatus("in progress");

            ProjectEntity project4 = new ProjectEntity();
            project4.setProjectName("Database Optimization");
            project4.setStartDate(startDate);
            project4.setEndDate(endDate);
            project4.setStatus("in progress");

            iProjectRepository.saveAll(List.of(project1, project2, project3, project4));

            TeamEntity team1 = new TeamEntity();
            team1.setTeamName("Team Pulsar");
            team1.setDescription("Pulsar omni");
            team1.setProjects(Collections.singletonList(project1));

            TeamEntity team2 = new TeamEntity();
            team2.setTeamName("Team Milestone");
            team2.setDescription("Milestone omni");
            team2.setProjects(Collections.singletonList(project2));

            TeamEntity team3 = new TeamEntity();
            team3.setTeamName("Team Mobile");
            team3.setDescription("Mobile Development Team");
            team3.setProjects(Collections.singletonList(project3));

            TeamEntity team4 = new TeamEntity();
            team4.setTeamName("Team Database");
            team4.setDescription("Database Optimization Team");
            team4.setProjects(Collections.singletonList(project4));

            iTeamRepository.saveAll(List.of(team1, team2, team3, team4));

            UserEntity user1 = new UserEntity();
            user1.setUserName("Sude Gokce");
            user1.setPassword("sude123");
            user1.setEmail("sude.gokcen@etiya.com");
            user1.setActive(true);
            user1.setTeam(team1);
            user1.setRoles(new HashSet<>(Set.of(RoleType.SOFTWARE_ARCHITECT, RoleType.TEAM_MEMBER)));

            UserEntity user2 = new UserEntity();
            user2.setUserName("Mehmet Emin");
            user2.setPassword("emin123");
            user2.setEmail("mehmet.emin@etiya.com");
            user2.setActive(true);
            user2.setTeam(team2);
            user2.setRoles(new HashSet<>(Set.of(RoleType.SOLUTION_ARCHITECT, RoleType.TEAM_MEMBER)));

            UserEntity user3 = new UserEntity();
            user3.setUserName("Erdem Onal");
            user3.setPassword("erdem123");
            user3.setEmail("erdem.onal@etiya.com");
            user3.setActive(true);
            user3.setTeam(team3);
            user3.setRoles(new HashSet<>(Set.of(RoleType.ANALYST, RoleType.TEAM_MEMBER)));

            UserEntity user4 = new UserEntity();
            user4.setUserName("Buse Keklik");
            user4.setPassword("buse123");
            user4.setEmail("buse.keklik@etiya.com");
            user4.setActive(true);
            user4.setTeam(team4);
            user4.setRoles(new HashSet<>(Set.of(RoleType.BACKEND, RoleType.TEAM_LEADER)));

            UserEntity user5 = new UserEntity();
            user5.setUserName("Enes Kasım");
            user5.setPassword("enes123");
            user5.setEmail("enes.kasim@etiya.com");
            user5.setActive(true);
            user5.setTeam(team4);
            user5.setRoles(new HashSet<>(Set.of(RoleType.TEAM_LEADER)));

            iUserRepository.saveAll(List.of(user1, user2, user3, user4, user5));

            PermissionEntity permission1 = new PermissionEntity();
            permission1.setPermissionName("READ");
            permission1.setDescription("Read permission");
            permission1.setRole(RoleType.ANALYST);

            PermissionEntity permission2 = new PermissionEntity();
            permission2.setPermissionName("WRITE");
            permission2.setDescription("Write permission");
            permission2.setRole(RoleType.BACKEND);

            PermissionEntity permission3 = new PermissionEntity();
            permission3.setPermissionName("DELETE");
            permission3.setDescription("Delete permission");
            permission3.setRole(RoleType.SOFTWARE_ARCHITECT);

            PermissionEntity permission4 = new PermissionEntity();
            permission4.setPermissionName("EXECUTE");
            permission4.setDescription("Execute permission");
            permission4.setRole(RoleType.SOLUTION_ARCHITECT);

            iPermissionRepository.saveAll(List.of(permission1, permission2, permission3, permission4));

            AbsenceEntity absence1 = new AbsenceEntity();
            absence1.setStartDate(startDate);
            absence1.setEndDate(endDate);
            absence1.setType("Sick Leave");
            absence1.setDescription("Sick leave");
            absence1.setUser(user1);

            AbsenceEntity absence2 = new AbsenceEntity();
            absence2.setStartDate(startDate);
            absence2.setEndDate(endDate);
            absence2.setType("Vacation");
            absence2.setDescription("Vacation");
            absence2.setUser(user2);

            iAbsenceRepository.saveAll(List.of(absence1, absence2));

            TaskEntity task1 = new TaskEntity();
            task1.setTaskName("Develop backend");
            task1.setStartDate(startDate);
            task1.setEndDate(endDate);
            task1.setManDays(10);
            task1.setCost(1000);
            task1.setSeverity("High");
            task1.setProgress(50.0);
            task1.setProject(project1);
            task1.setUsers(Collections.singletonList(user1));

            TaskEntity task2 = new TaskEntity();
            task2.setTaskName("Develop frontend");
            task2.setStartDate(startDate);
            task2.setEndDate(endDate);
            task2.setManDays(20);
            task2.setCost(2000);
            task2.setSeverity("Medium");
            task2.setProgress(75.0);
            task2.setProject(project2);
            task2.setUsers(Collections.singletonList(user2));

            TaskEntity task3 = new TaskEntity();
            task3.setTaskName("Design architecture");
            task3.setStartDate(startDate);
            task3.setEndDate(endDate);
            task3.setManDays(15);
            task3.setCost(1500);
            task3.setSeverity("Critical");
            task3.setProgress(60.0);
            task3.setProject(project3);
            task3.setUsers(Collections.singletonList(user3));

            TaskEntity task4 = new TaskEntity();
            task4.setTaskName("Optimize database");
            task4.setStartDate(startDate);
            task4.setEndDate(endDate);
            task4.setManDays(25);
            task4.setCost(2500);
            task4.setSeverity("High");
            task4.setProgress(40.0);
            task4.setProject(project4);
            task4.setUsers(Collections.singletonList(user4));

            TaskEntity task5 = new TaskEntity();
            task5.setTaskName("Devops");
            task5.setStartDate(startDate);
            task5.setEndDate(endDate);
            task5.setManDays(10);
            task5.setCost(1000);
            task5.setSeverity("High");
            task5.setProgress(50.0);
            task5.setProject(project1);
            task5.setUsers(Collections.singletonList(user1));

            iTaskRepository.saveAll(List.of(task1, task2, task3, task4, task5));

            AlertEntity alert1 = new AlertEntity();
            alert1.setAlertDate(new Date());
            alert1.setMessage("Task deadline");
            alert1.setTask(task1);

            AlertEntity alert2 = new AlertEntity();
            alert2.setAlertDate(new Date());
            alert2.setMessage("New task assigned");
            alert2.setTask(task2);

            iAlertRepository.saveAll(List.of(alert1, alert2));
        };
    }
}
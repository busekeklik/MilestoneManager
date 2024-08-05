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

// LOMBOK
@RequiredArgsConstructor

// Command Line runner
@Configuration
@Log4j2
public class MainRunner {

    private final IUserRepository iUserRepository;
    private final IProjectRepository iProjectRepository;
    private final ITeamRepository iTeamRepository;
    private final IAbsenceRepository iAbsenceRepository;
    private final IAlertRepository iAlertRepository;
    private final IPermissionRepository iPermissionRepository;
    private final IRoleRepository iRoleRepository;
    private final ITaskRepository iTaskRepository;

    // START
    public void start() {
        log.info("###START#######");
    }

    @Bean
    public CommandLineRunner milestone() {
        // Lambda Expression
        return args -> {
            log.info("projects");
            System.out.println("users");

            // Calendar
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, 2024);
            calendar.set(Calendar.MONTH, Calendar.JULY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date startDate = calendar.getTime();
            calendar.set(Calendar.DAY_OF_MONTH, 30);
            Date endDate = calendar.getTime();

            // Projects
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

            iProjectRepository.save(project1);
            iProjectRepository.save(project2);

            // Teams
            TeamEntity team1 = new TeamEntity();
            team1.setTeamName("Team Pulsar");
            team1.setDescription("Pulsar omni");
            team1.setProjects(Collections.singletonList(project1));

            TeamEntity team2 = new TeamEntity();
            team2.setTeamName("Team Milestone");
            team2.setDescription("Milestone omni");
            team2.setProjects(Collections.singletonList(project2));

            iTeamRepository.save(team1);
            iTeamRepository.save(team2);

            // Users
            UserEntity user1 = new UserEntity();
            user1.setUserName("SudeGokce");
            user1.setPassword("sude123");
            user1.setEmail("sude.gokcen@etiya.com");
            user1.setActive(true);
            user1.setTeam(team1);

            UserEntity user2 = new UserEntity();
            user2.setUserName("MehmetEmin");
            user2.setPassword("emin123");
            user2.setEmail("mehmet.emin@etiya.com");
            user2.setActive(true);
            user2.setTeam(team2);

            iUserRepository.save(user1);
            iUserRepository.save(user2);

            // Roles
            RoleEntity role1 = new RoleEntity();
            role1.setRoleName("Admin");
            role1.setDescription("Admin role");
            role1.setUser(user1);

            RoleEntity role2 = new RoleEntity();
            role2.setRoleName("FE");
            role2.setDescription("FE role");
            role2.setUser(user2);

            iRoleRepository.save(role1);
            iRoleRepository.save(role2);

            // Permissions
            PermissionEntity permission1 = new PermissionEntity();
            permission1.setPermissionName("READ");
            permission1.setDescription("Read permission");
            permission1.setRole(role1);

            PermissionEntity permission2 = new PermissionEntity();
            permission2.setPermissionName("WRITE");
            permission2.setDescription("Write permission");
            permission2.setRole(role2);

            iPermissionRepository.save(permission1);
            iPermissionRepository.save(permission2);

            // Linking roles with permissions
            role1.setPermissions(Collections.singletonList(permission1));
            role2.setPermissions(Collections.singletonList(permission2));

            iRoleRepository.save(role1);
            iRoleRepository.save(role2);

            // Absences
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

            iAbsenceRepository.save(absence1);
            iAbsenceRepository.save(absence2);

            // Tasks
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

            iTaskRepository.save(task1);
            iTaskRepository.save(task2);

            // Alerts
            AlertEntity alert1 = new AlertEntity();
            alert1.setAlertDate(new Date());
            alert1.setMessage("Task deadline");
            alert1.setTask(task1);

            AlertEntity alert2 = new AlertEntity();
            alert2.setAlertDate(new Date());
            alert2.setMessage("New task assigned");
            alert2.setTask(task2);

            iAlertRepository.save(alert1);
            iAlertRepository.save(alert2);
        };
    }
}

package com.etiya.milestonemanager.runner;

import com.etiya.milestonemanager.data.entity.*;
import com.etiya.milestonemanager.data.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

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
            log.info("Initializing data for OMNI project and associated entities");
            System.out.println("Running milestone setup...");

            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, 2024);
            calendar.set(Calendar.MONTH, Calendar.JULY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date startDate = calendar.getTime();
            calendar.set(Calendar.DAY_OF_MONTH, 30);
            Date endDate = calendar.getTime();

            // Create and save single project
            ProjectEntity project = new ProjectEntity();
            project.setProjectName("OMNI");
            project.setStartDate(startDate);
            project.setEndDate(endDate);
            project.setStatus("in progress");
            iProjectRepository.save(project);

            // Create and save team associated with the project
            TeamEntity team = new TeamEntity();
            team.setTeamName("Team Pulsar");
            team.setDescription("Pulsar omni");
            team.setProjects(Collections.singletonList(project));
            iTeamRepository.save(team);

            // Create and save users
            UserEntity user1 = new UserEntity();
            user1.setUserName("Buse Keklik");
            user1.setPassword("buse123");
            user1.setEmail("buse.keklik@etiya.com");
            user1.setActive(true);
            user1.setTeam(team);
            user1.setRoles(Set.of(RoleType.BACKEND, RoleType.TEAM_LEADER));
            iUserRepository.save(user1);

            UserEntity user2 = new UserEntity();
            user2.setUserName("Erdem Önal");
            user2.setPassword("erdem123");
            user2.setEmail("erdem.onal@etiya.com");
            user2.setActive(true);
            user2.setTeam(team);
            user2.setRoles(Set.of(RoleType.FRONTEND));
            iUserRepository.save(user2);

            UserEntity user3 = new UserEntity();
            user3.setUserName("Sude Gökçen");
            user3.setPassword("sude123");
            user3.setEmail("sude.gokcen@etiya.com");
            user3.setActive(true);
            user3.setTeam(team);
            user3.setRoles(Set.of(RoleType.BACKEND));
            iUserRepository.save(user3);

            UserEntity user4 = new UserEntity();
            user4.setUserName("Enes Kasım");
            user4.setPassword("enes123");
            user4.setEmail("enes.kasim@etiya.com");
            user4.setActive(true);
            user4.setTeam(team);
            user4.setRoles(Set.of(RoleType.DEVOPS));
            iUserRepository.save(user4);

            UserEntity user5 = new UserEntity();
            user5.setUserName("Mehmet Emin");
            user5.setPassword("mehmet123");
            user5.setEmail("mehmet.emin@etiya.com");
            user5.setActive(true);
            user5.setTeam(team);
            user5.setRoles(Set.of(RoleType.BACKEND));
            iUserRepository.save(user5);

            // Create and save tasks with associated users
            TaskEntity task1 = new TaskEntity();
            task1.setTaskName("Develop backend services for OMNI");
            task1.setStartDate(startDate);
            task1.setEndDate(endDate);
            task1.setManDays(10);
            task1.setCost(1000);
            task1.setSeverity(3);  // High
            task1.setProgress(50.0);
            task1.setProject(project);
            task1.setSoftwareArchitects(Collections.singletonList(user1));  // Assign Buse Keklik as a software architect
            iTaskRepository.save(task1);

            TaskEntity task2 = new TaskEntity();
            task2.setTaskName("Design frontend UI for OMNI");
            task2.setStartDate(startDate);
            task2.setEndDate(endDate);
            task2.setManDays(8);
            task2.setCost(800);
            task2.setSeverity(2);  // Medium
            task2.setProgress(40.0);
            task2.setProject(project);
            task2.setSoftwareArchitects(Collections.singletonList(user2));  // Assign Erdem Önal as a software architect
            iTaskRepository.save(task2);

            TaskEntity task3 = new TaskEntity();
            task3.setTaskName("Integrate API for OMNI");
            task3.setStartDate(startDate);
            task3.setEndDate(endDate);
            task3.setManDays(12);
            task3.setCost(1200);
            task3.setSeverity(3);  // High
            task3.setProgress(60.0);
            task3.setProject(project);
            task3.setSoftwareArchitects(Collections.singletonList(user3));  // Assign Sude Gökçen as a software architect
            iTaskRepository.save(task3);

            TaskEntity task4 = new TaskEntity();
            task4.setTaskName("Setup CI/CD pipeline for OMNI");
            task4.setStartDate(startDate);
            task4.setEndDate(endDate);
            task4.setManDays(5);
            task4.setCost(500);
            task4.setSeverity(1);  // Low
            task4.setProgress(70.0);
            task4.setProject(project);
            task4.setSoftwareArchitects(Collections.singletonList(user4));  // Assign Enes Kasım as a software architect
            iTaskRepository.save(task4);

            TaskEntity task5 = new TaskEntity();
            task5.setTaskName("Database design for OMNI");
            task5.setStartDate(startDate);
            task5.setEndDate(endDate);
            task5.setManDays(7);
            task5.setCost(700);
            task5.setSeverity(2);  // Medium
            task5.setProgress(80.0);
            task5.setProject(project);
            task5.setSoftwareArchitects(Collections.singletonList(user5));  // Assign Mehmet Emin as a software architect
            iTaskRepository.save(task5);

            // Create and save specific absences per user
            // Absences for Buse Keklik
            createAbsenceForUser(user1, 3, 7);
            createAbsenceForUser(user1, 14, 18);
            createAbsenceForUser(user1, 21, 25);

            // Absences for Erdem Önal
            createAbsenceForUser(user2, 5, 9);
            createAbsenceForUser(user2, 12, 16);
            createAbsenceForUser(user2, 19, 23);

            // Absences for Sude Gökçen
            createAbsenceForUser(user3, 7, 11);
            createAbsenceForUser(user3, 14, 18);
            createAbsenceForUser(user3, 21, 25);

            // Absences for Enes Kasım
            createAbsenceForUser(user4, 4, 8);
            createAbsenceForUser(user4, 11, 15);
            createAbsenceForUser(user4, 18, 22);

            // Absences for Mehmet Emin
            createAbsenceForUser(user5, 6, 10);
            createAbsenceForUser(user5, 13, 17);
            createAbsenceForUser(user5, 20, 24);

            // Create and save alerts
            AlertEntity alert = new AlertEntity();
            alert.setAlertDate(new Date());
            alert.setMessage("Task deadline approaching for OMNI project");
            alert.setTask(task1);  // Example alert for the first task
            iAlertRepository.save(alert);
        };
    }

    private void createAbsenceForUser(UserEntity user, int startDay, int endDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.JULY);

        calendar.set(Calendar.DAY_OF_MONTH, startDay);
        Date absenceStartDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, endDay);
        Date absenceEndDate = calendar.getTime();

        AbsenceEntity absence = new AbsenceEntity();
        absence.setStartDate(absenceStartDate);
        absence.setEndDate(absenceEndDate);
        absence.setDescription("Personal leave");
        absence.setType("Paid");
        absence.setUser(user);
        iAbsenceRepository.save(absence);
    }
}



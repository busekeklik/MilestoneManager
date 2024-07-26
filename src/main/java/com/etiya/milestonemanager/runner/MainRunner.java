package com.etiya.milestonemanager.runner;

import com.etiya.milestonemanager.data.entity.ProjectEntity;
import com.etiya.milestonemanager.data.entity.TeamEntity;
import com.etiya.milestonemanager.data.repository.IProjectRepository;
import com.etiya.milestonemanager.data.repository.ITeamRepository;
import com.etiya.milestonemanager.data.repository.IUserRepository;
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

    // START
    public void start(){
        log.info("###START#######");
    }

    @Bean
    public CommandLineRunner milestone(){
        // Lambda Expression
        return args -> {
            log.info("projects");
            System.out.println("users");

            //Project

            //Calendar
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, 2024);
            calendar.set(Calendar.MONTH, Calendar.JULY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);


            ProjectEntity project1 = new ProjectEntity();
            project1.setProjectName("Backend");
            Date startDate = calendar.getTime();
            project1.setStartDate(startDate);
            Date endDate = calendar.getTime();
            project1.setEndDate(endDate);
            project1.setStatus("in progress");

            TeamEntity team1 = new TeamEntity();
            team1.setDescription("Beelzebub");
            team1.setTeamName("Pulsar");
            team1.setProjects(Collections.singletonList(project1));

            iProjectRepository.save(project1);
            iTeamRepository.save(team1);
        };
    }
}

package com.etiya.milestonemanager.runner;

import com.etiya.milestonemanager.data.entity.ProjectEntity;
import com.etiya.milestonemanager.data.entity.TaskEntity;
import com.etiya.milestonemanager.data.entity.UserEntity;
import com.etiya.milestonemanager.data.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Date;

// LOMBOK
@RequiredArgsConstructor

// Command Line runner
@Configuration
@Log4j2
public class MainRunner {

    private final IUserRepository iUserRepository;

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

            /* PROJECT */

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

            /* TASK */

            TaskEntity task1 = new TaskEntity();
            task1.setTaskName("Repository");
            task1.setStartDate(startDate);
            task1.setEndDate(endDate);
            task1.setProgress(45.6);
            task1.setSeverity("high");
            task1.setManDays(3);

            /* USER */
            UserEntity user1 = new UserEntity();
            user1.setUserName("Buse");
            user1.setEmail("busekeklik23@gmail.com");
            user1.setPassword("busesu");
            user1.setActive(true);
        };
    }
}
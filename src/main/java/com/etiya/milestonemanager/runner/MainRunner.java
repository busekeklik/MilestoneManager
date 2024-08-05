package com.etiya.milestonemanager.runner;

import com.etiya.milestonemanager.data.entity.ProjectEntity;
import com.etiya.milestonemanager.data.entity.UserEntity;
import com.etiya.milestonemanager.data.repository.IProjectRepository;
import com.etiya.milestonemanager.data.repository.ITeamRepository;
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
    private final IProjectRepository iProjectRepository;

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

            //Calendar
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, 2024);
            calendar.set(Calendar.MONTH, Calendar.JULY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            //PROJECT
            ProjectEntity project1 = new ProjectEntity();
            project1.setProjectName("Backend");
            Date startDate1 = calendar.getTime();
            project1.setStartDate(startDate1);
            Date endDate1 = calendar.getTime();
            project1.setEndDate(endDate1);
            project1.setStatus("in progress");
            iProjectRepository.save(project1);

            ProjectEntity project2 = new ProjectEntity();
            project2.setProjectName("Frontend");
            Date startDate2 = calendar.getTime();
            project2.setStartDate(startDate2);
            Date endDate2 = calendar.getTime();
            project2.setEndDate(endDate2);
            project2.setStatus("in progress");
            iProjectRepository.save(project2);
            //PROJECT

            //USER
            UserEntity user1 = new UserEntity();
            user1.setUserName("Buse Keklik");
            user1.setPassword("user1");
            user1.setEmail("busekeklik@etiya.com.tr");
            user1.setActive(true);
            iUserRepository.save(user1);

            UserEntity user2 = new UserEntity();
            user2.setUserName("Erdem Önal");
            user2.setPassword("user2");
            user2.setEmail("erdemonal@etiya.com.tr");
            user2.setActive(true);
            iUserRepository.save(user2);

            UserEntity user3 = new UserEntity();
            user3.setUserName("Gökçen Sude Yenidünya");
            user3.setPassword("user3");
            user3.setEmail("gokcensudeyenidunya@etiya.com.tr");
            user3.setActive(true);
            iUserRepository.save(user3);
        };
    }
}

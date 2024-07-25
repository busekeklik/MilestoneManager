package com.etiya.milestonemanager.runner;

import com.etiya.milestonemanager.data.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            log.info("users");
            System.out.println("users");

        };
    }
}

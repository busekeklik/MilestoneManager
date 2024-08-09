package com.etiya.milestonemanager.alert.impl;

import com.etiya.milestonemanager.data.entity.AlertEntity;
import com.etiya.milestonemanager.data.entity.TaskEntity;
import com.etiya.milestonemanager.data.repository.IAlertRepository;
import com.etiya.milestonemanager.alert.IAlertTest;
import com.etiya.milestonemanager.data.repository.ITaskRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AlertTestImpl implements IAlertTest {

    @Autowired
    private IAlertRepository alertRepository;

    @Autowired
    private ITaskRepository taskRepository;

    private AlertEntity alertEntity;

    @BeforeAll
    static void setUpBeforeAll() {
        log.info("Starting Alert Tests...");
    }

    @BeforeEach
    void setUpBeforeEach() {
        log.info("Setting up for an Alert test case...");

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName("Sample Task");
        taskEntity.setStartDate(new Date());
        taskEntity.setEndDate(new Date());
        taskEntity.setManDays(5);
        taskEntity.setCost(1000);
        taskEntity.setSeverity(3); // Changed to int, e.g., 3 for High
        taskEntity.setProgress(50.0);
        taskRepository.save(taskEntity);

        alertEntity = new AlertEntity();
        alertEntity.setTask(taskEntity);
        alertEntity.setAlertDate(new Date());
        alertEntity.setMessage("Reminder: Update status");
        alertRepository.save(alertEntity);
    }

    @Test
    @Order(1)
    @Tag("create")
    @Override
    public void AlertCreateTest() {
        assertNotNull(alertRepository.findById(alertEntity.getAlertID()).get());
        log.info("Alert creation test passed.");
    }

    @Test
    @Order(2)
    @Tag("find")
    @Override
    public void AlertFindTest() {
        AlertEntity foundAlert = alertRepository.findById(alertEntity.getAlertID()).get();
        assertEquals("Reminder: Update status", foundAlert.getMessage());
        log.info("Alert find test passed.");
    }

    @Test
    @Order(3)
    @Tag("list")
    @Override
    public void AlertListTest() {
        Iterable<AlertEntity> alerts = alertRepository.findAll();
        assertThat(alerts).hasSizeGreaterThan(0);
        log.info("Alert listing test passed.");
    }

    @Test
    @Order(4)
    @Tag("update")
    @Override
    public void AlertUpdateTest() {
        alertEntity.setMessage("Updated Reminder: Complete the report.");
        alertRepository.save(alertEntity);
        assertNotEquals("Reminder: Update status", alertRepository.findById(alertEntity.getAlertID()).get().getMessage());
        log.info("Alert update test passed.");
    }

    @Test
    @Order(5)
    @Tag("delete")
    @Override
    public void AlertDeleteTest() {
        alertRepository.deleteById(alertEntity.getAlertID());
        assertThat(alertRepository.existsById(alertEntity.getAlertID())).isFalse();
        log.info("Alert deletion test passed.");
    }
}

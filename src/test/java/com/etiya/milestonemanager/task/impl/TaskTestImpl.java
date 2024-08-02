// TaskTestImpl

package com.etiya.milestonemanager.task.impl;

import com.etiya.milestonemanager.business.dto.TaskDto;
import com.etiya.milestonemanager.business.services.ITaskServices;
import com.etiya.milestonemanager.data.entity.TaskEntity;
import com.etiya.milestonemanager.data.repository.ITaskRepository;
import com.etiya.milestonemanager.task.ITaskTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TaskTestImpl implements ITaskTest {

    @Autowired
    private ITaskServices<TaskDto, TaskEntity> taskServices;

    @Autowired
    private ITaskRepository taskRepository;

    private TaskEntity taskEntity;

    @BeforeAll
    static void setUpBeforeAll() {
        log.info("Starting Task Tests...");
    }

    @BeforeEach
    void setUpBeforeEach() {
        log.info("Preparing for a Task test...");
        taskEntity = new TaskEntity();
        taskEntity.setTaskName("Initial Setup");
        taskEntity.setStartDate(new Date());
        taskEntity.setEndDate(new Date());
        taskEntity.setManDays(3);
        taskEntity.setCost(1500);
        taskEntity.setSeverity("Medium");
        taskEntity.setProgress(0.0);
        taskRepository.save(taskEntity);
    }

    @Test
    @Order(1)
    @Tag("create")
    @Override
    public void TaskCreateTest() {
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskName("Development Phase");
        taskDto.setStartDate(new Date());
        taskDto.setEndDate(new Date());
        taskDto.setManDays(5);
        taskDto.setCost(3000);
        taskDto.setSeverity("High");
        taskDto.setProgress(25.0);
        TaskDto createdTask = taskServices.taskServiceCreate(taskDto);
        assertNotNull(createdTask);
        log.info("Task creation test passed.");
    }

    @Test
    @Order(2)
    @Tag("find")
    @Override
    public void TaskFindTest() {
        TaskDto foundTask = taskServices.taskServiceFindById(taskEntity.getTaskID());
        assertNotNull(foundTask);
        assertEquals("Initial Setup", foundTask.getTaskName());
        log.info("Task find test passed.");
    }

    @Test
    @Order(3)
    @Tag("list")
    @Override
    public void TaskListTest() {
        List<TaskDto> tasks = taskServices.taskServiceList();
        assertThat(tasks).isNotEmpty();
        log.info("Task listing test passed.");
    }

    @Test
    @Order(4)
    @Tag("update")
    @Override
    public void TaskUpdateTest() {
        TaskDto taskDto = taskServices.taskServiceFindById(taskEntity.getTaskID());
        taskDto.setTaskName("Final Review");
        taskDto.setProgress(80.0);
        TaskDto updatedTask = taskServices.taskServiceUpdateById(taskEntity.getTaskID(), taskDto);
        assertNotNull(updatedTask);
        assertEquals("Final Review", updatedTask.getTaskName());
        log.info("Task update test passed.");
    }

    @Test
    @Order(5)
    @Tag("delete")
    @Override
    public void TaskDeleteTest() {
        taskServices.taskServiceDeleteById(taskEntity.getTaskID());
        assertThrows(Exception.class, () -> taskServices.taskServiceFindById(taskEntity.getTaskID()));
        log.info("Task deletion test passed.");
    }
}
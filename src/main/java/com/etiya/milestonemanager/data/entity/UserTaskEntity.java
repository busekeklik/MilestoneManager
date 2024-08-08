package com.etiya.milestonemanager.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@Log4j2
@Entity
@Table(name = "user_task")
public class UserTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userID;

    @Column(name = "task_id", nullable = false)
    private Long taskID;
}

package com.etiya.milestonemanager.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Log4j2

@Entity
@Table(name = "alerts")
public class AlertEntity implements Serializable {
    public static final Long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id", unique = true, nullable = false, updatable = false)
    private Long alertID;

    @Column(name = "task_id", nullable = false)
    private Long taskID;

    @Column(name = "alert_date", nullable = false)
    private Date alertDate;

    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="task_id",nullable = false)
    private TaskEntity relationTaskEntity;
}

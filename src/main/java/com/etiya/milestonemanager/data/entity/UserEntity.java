package com.etiya.milestonemanager.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.List;


//lombok
@Getter
@Setter
@Log4j2

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    public static final Long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private Long userID;

    @Column(name = "user_name",nullable = false)
    private String userName;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "isActive",nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="team_id",nullable = false)
    private TeamEntity relationTeamEntity;

    @OneToMany(mappedBy = "relationUserEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<AbsenceEntity> relationAbsenceEntityList;

    @OneToMany(mappedBy = "relationUserEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<AbsenceEntity> relationRoleEntityList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_task",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<TaskEntity> tasks;
}

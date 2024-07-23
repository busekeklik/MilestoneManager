package com.etiya.milestonemanager.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

//lombok
@Getter
@Setter
@Log4j2

@Entity
@Table(name = "permissions")
public class PermissionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id", unique = true, nullable = false, updatable = false)
    private Long permissionID;

    @Column(name = "permission_name",nullable = false)
    private String permissionName;

    @Column(name = "description")
    private String description;
}

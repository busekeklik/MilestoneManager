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
@Table(name = "roles")
public class RoleEntity implements Serializable {

    public static final Long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false, updatable = false)
    private Long roleID;

    @Column(name = "role_name",nullable = false)
    private String roleName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "relationRoleEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<PermissionEntity> relationPermissionEntityList;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    private UserEntity relationUserEntity;
}

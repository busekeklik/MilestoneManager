package com.etiya.milestonemanager.business.dto;

import com.etiya.milestonemanager.data.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Set;

// Lombok annotations for generating boilerplate code
@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class UserDto implements Serializable {

    public static final Long serialVersionUID = 1L;

    private Long userID;

    private String userName;

    private String password;

    private String email;

    private boolean isActive;

    private Set<RoleType> roles;

    // Add this field to store the team ID
    private Long teamId;
}

package com.etiya.milestonemanager.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class PermissionDto implements Serializable {

    public static final Long serialVersionUID = 1L;

    private Long permissionID;
    private String permissionName;
    private String description;
}

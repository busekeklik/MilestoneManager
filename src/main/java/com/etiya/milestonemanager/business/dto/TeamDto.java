package com.etiya.milestonemanager.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class TeamDto implements Serializable {

    public static final Long serialVersionUID = 1L;

    private Long teamID;
    private String teamName;
    private String description;
    private List<Long> projectIds;
    private List<UserDto> members; // Add this to include team members
}

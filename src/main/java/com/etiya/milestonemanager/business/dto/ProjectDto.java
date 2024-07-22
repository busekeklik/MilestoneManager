package com.etiya.milestonemanager.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class ProjectDto implements Serializable {
    public static final Long serialVersionUID = 1L;

    private Long projectId;
    private String projectName;
    private Date startDate;
    private Date endDate;
    private String status;
}

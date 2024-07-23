package com.etiya.milestonemanager.business.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Date;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2

public class AbsenceDto implements Serializable {

    public static final Long serialVersionUID = 1L;

    private Long absenceID;

    private Date startDate;
    private Date endDate;
    private String type;
    private String description;

}

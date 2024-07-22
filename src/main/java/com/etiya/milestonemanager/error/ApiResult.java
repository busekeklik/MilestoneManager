package com.etiya.milestonemanager.error;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

//lombok
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult {

    // Field: s e m  p v c
    private Integer status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> validationErrors;
    private String createdDate = nowDate();

    // Constructor with s  m  p parameter
    public ApiResult(Integer status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        //createdDate = new Date(System.currentTimeMillis());
    }

    // Constructor with s e m  p parameter
    public ApiResult(Integer status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Now Date
    private String nowDate() {
        Locale locale=new Locale("tr","TR");
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        Date date=new Date();
        return  dateFormat.format(date);
    }
}

package com.etiya.milestonemanager.audit;
/*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

// LOMBOK
@Getter
@Setter

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract public class AuditingAwareBaseEntity  implements Serializable {

    public static final Long serialVersionUID = 1L;

    //@JsonIgnore
    @CreatedBy
    @Column(name = "created_user")
    protected String createdUser;

    //@JsonIgnore
    @CreatedDate
    @Column(name = "created_date")
    protected Date createdDate;

    //@JsonIgnore
    @LastModifiedBy
    @Column(name = "last_user")
    protected String lastUser;


    //@JsonIgnore
    @LastModifiedDate
    @Column(name = "last_date")
    protected Date lastDate;
}
*/
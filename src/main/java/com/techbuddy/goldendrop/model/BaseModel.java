package com.techbuddy.goldendrop.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import java.sql.Timestamp;

@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {

    @Column(name = "created_at")
    @CreationTimestamp
    public Timestamp createdDate;

}

package com.supercoding.shoppingmallbackend.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
public class CommonField {
    @NotNull
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", insertable = false)
    private Timestamp modifiedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
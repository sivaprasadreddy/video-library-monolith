package com.sivalabs.videolibrary.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
public abstract class BaseEntity implements Serializable {

    @Override
    public int hashCode() {
        return 31;
    }

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(insertable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

package com.relatos.ms_books_catalogue.domains.commons;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class SoftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @NotNull
    @CreatedDate
    @Basic(optional = false)
    @ColumnDefault("CURRENT_DATE")
    private ZonedDateTime createdDate;

    @NotNull
    @LastModifiedDate
    @Basic(optional = false)
    @ColumnDefault("CURRENT_DATE")
    private ZonedDateTime lastUpdateDate;

    @NotNull
    @Basic(optional = false)
    @ColumnDefault("false")
    private Boolean deleted = false;


}

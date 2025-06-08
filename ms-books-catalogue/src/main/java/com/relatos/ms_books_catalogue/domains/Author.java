package com.relatos.ms_books_catalogue.domains;

import com.relatos.ms_books_catalogue.domains.commons.SoftEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author extends SoftEntity {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

}

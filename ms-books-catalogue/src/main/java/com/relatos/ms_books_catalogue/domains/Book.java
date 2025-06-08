package com.relatos.ms_books_catalogue.domains;

import com.fasterxml.jackson.databind.node.DoubleNode;
import com.relatos.ms_books_catalogue.domains.commons.SoftEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Documented;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "ISBN", name = "books_ISBN"))
public class Book extends SoftEntity {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String ISBN;

    @NotNull
    private ZonedDateTime publishedDate;

    @NotNull
    private Integer stock;

    @NotNull
    private Double price;

    private Double rating;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

}

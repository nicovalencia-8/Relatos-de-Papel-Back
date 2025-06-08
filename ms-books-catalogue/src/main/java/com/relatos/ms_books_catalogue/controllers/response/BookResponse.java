package com.relatos.ms_books_catalogue.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String description;
    private Double price;
    private String ISBN;
    private List<String> category;
    private Integer stock;
    private String img_url;

}

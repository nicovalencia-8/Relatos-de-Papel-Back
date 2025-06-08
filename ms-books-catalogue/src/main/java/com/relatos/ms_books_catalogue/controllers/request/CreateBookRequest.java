package com.relatos.ms_books_catalogue.controllers.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    @NotBlank(message = "Title can not be empty")
    private String title;

    @NotBlank(message = "Description can not be empty")
    private String description;

    @NotBlank(message = "ISBN can not be empty")
    private String ISBN;

    @NotBlank(message = "Published Date can not be empty")
    private ZonedDateTime publishedDate;

    @NotBlank(message = "Stock Date can not be empty")
    private Integer stock;

    @NotBlank(message = "Price Date can not be empty")
    private Double price;

    private Double rating;

    @Valid
    private CreateAuthorRequest author;

    @NotBlank(message = "Image Date can not be empty")
    private String image;

    @Valid
    private List<String> category;

}

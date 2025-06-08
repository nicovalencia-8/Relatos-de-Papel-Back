package com.relatos.ms_books_catalogue.services;

import com.relatos.ms_books_catalogue.controllers.request.CreateCategoryRequest;
import com.relatos.ms_books_catalogue.domains.Category;
import com.relatos.ms_books_catalogue.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategories(CreateCategoryRequest categoryRequest) {
        Category category = categoryRepository.findByCategoryName(categoryRequest.getName());
        if (category == null) {
            category = new Category(categoryRequest);
            return categoryRepository.save(category);
        } else {
            throw new IllegalArgumentException("La categoria ya existe");
        }
    }

}

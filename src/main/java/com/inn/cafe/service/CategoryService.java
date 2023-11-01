package com.inn.cafe.service;

import com.inn.cafe.POJO.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
/**
 * Service interface for managing categories.
 */
public interface CategoryService {

    /**
     * Adds a new category based on the provided request map.
     *
     * @param requestMap A map containing data for creating a new category.
     * @return A ResponseEntity with a string response indicating the result of the category addition.
     */
    ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

    /**
     * Retrieves a list of categories, optionally filtered by a filter value.
     *
     * @param filterValue (Optional) A filter value to narrow down the category list.
     * @return A ResponseEntity containing a list of Category objects.
     */
    ResponseEntity<List<Category>> getAllCategory(String filterValue);

    /**
     * Updates an existing category based on the provided request map.
     *
     * @param requestMap A map containing data for updating an existing category.
     * @return A ResponseEntity with a string response indicating the result of the update.
     */
    ResponseEntity<String> updateCategory(Map<String, String> requestMap);
}


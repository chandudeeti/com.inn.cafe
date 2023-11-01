package com.inn.cafe.rest;

import com.inn.cafe.POJO.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST API interface for managing categories.
 */
@RequestMapping(path = "/category")
public interface CategoryRest {

    /**
     * Adds a new category based on the provided request map.
     *
     * @param requestMap A map containing data for creating a new category.
     * @return A ResponseEntity with a string response indicating the result.
     */
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewCategory(@RequestBody(required = true) Map<String, String> requestMap);

    /**
     * Retrieves a list of all categories, optionally filtered by a filter value.
     *
     * @param filterValue (Optional) A filter value to narrow down the category list.
     * @return A ResponseEntity containing a list of Category objects.
     */
    @GetMapping(path = "/get")
    ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filterValue);

    /**
     * Updates an existing category based on the provided request map.
     *
     * @param requestMap A map containing data for updating an existing category.
     * @return A ResponseEntity with a string response indicating the result.
     */
    @PostMapping(path = "/update")
    ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String, String> requestMap);
}


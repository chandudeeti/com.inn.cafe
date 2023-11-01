package com.inn.cafe.restImpl;

import com.inn.cafe.POJO.Category;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.rest.CategoryRest;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Implementation of the CategoryRest interface for managing categories via REST endpoints.
 */
@RestController
public class CategoryRestImpl implements CategoryRest {

    @Autowired
    CategoryService categoryService;

    /**
     * Adds a new category based on the provided request map.
     *
     * @param requestMap A map containing data for creating a new category.
     * @return A ResponseEntity with a string response indicating the result of the category addition.
     */
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        return categoryService.addNewCategory(requestMap);
    }

    /**
     * Retrieves a list of categories, optionally filtered by a filter value.
     *
     * @param filterValue (Optional) A filter value to narrow down the category list.
     * @return A ResponseEntity containing a list of Category objects.
     */
    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        return categoryService.getAllCategory(filterValue);
    }

    /**
     * Updates an existing category based on the provided request map.
     *
     * @param requestMap A map containing data for updating an existing category.
     * @return A ResponseEntity with a string response indicating the result of the update.
     */
    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            return categoryService.updateCategory(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

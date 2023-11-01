package com.inn.cafe.rest;

import com.inn.cafe.POJO.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * REST API interface for managing products.
 */
@RequestMapping(path = "/product")
public interface ProductRest {

    /**
     * Adds a new product based on the provided request map.
     *
     * @param requestMap A map containing data for creating a new product.
     * @return A ResponseEntity with a string response indicating the result.
     */
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewProduct(@RequestBody Map<String, String> requestMap);

    /**
     * Retrieves a list of all products, typically represented as ProductWrapper objects.
     *
     * @return A ResponseEntity containing a list of ProductWrapper objects.
     */
    @GetMapping(path = "/get")
    ResponseEntity<List<ProductWrapper>> getAllProduct();

    /**
     * Updates an existing product based on the provided request map.
     *
     * @param requestMap A map containing data for updating an existing product.
     * @return A ResponseEntity with a string response indicating the result.
     */
    @PostMapping(path = "/update")
    ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return A ResponseEntity with a string response indicating the result.
     */
    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    /**
     * Updates the status of a product based on the provided request map.
     *
     * @param requestMap A map containing data for updating the status of a product.
     * @return A ResponseEntity with a string response indicating the result.
     */
    @PostMapping(path = "/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

    /**
     * Retrieves a list of products within a specific category based on the category ID.
     *
     * @param id The ID of the category for which to retrieve products.
     * @return A ResponseEntity containing a list of ProductWrapper objects.
     */
    @GetMapping(path = "/getByCategory/{id}")
    ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer id);

    /**
     * Retrieves a specific product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return A ResponseEntity containing a single ProductWrapper object.
     */
    @GetMapping(path = "/getById/{id}")
    ResponseEntity<ProductWrapper> getProductById(@PathVariable Integer id);
}

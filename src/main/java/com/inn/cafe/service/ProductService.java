package com.inn.cafe.service;

import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
/**
 * Service interface for managing products.
 */
public interface ProductService {

    /**
     * Adds a new product based on the provided request map.
     *
     * @param requestMap A map containing data for creating a new product.
     * @return A ResponseEntity with a string response indicating the result of the product addition.
     */
    ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

    /**
     * Retrieves a list of all products, typically represented as ProductWrapper objects.
     *
     * @return A ResponseEntity containing a list of ProductWrapper objects.
     */
    ResponseEntity<List<ProductWrapper>> getAllProduct();

    /**
     * Updates an existing product based on the provided request map.
     *
     * @param requestMap A map containing data for updating an existing product.
     * @return A ResponseEntity with a string response indicating the result of the update.
     */
    ResponseEntity<String> updateProduct(Map<String, String> requestMap);

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return A ResponseEntity with a string response indicating the result.
     */
    ResponseEntity<String> deleteProduct(Integer id);

    /**
     * Updates the status of a product based on the provided request map.
     *
     * @param requestMap A map containing data for updating the status of a product.
     * @return A ResponseEntity with a string response indicating the result.
     */
    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    /**
     * Retrieves a list of products within a specific category based on the category ID.
     *
     * @param id The ID of the category for which to retrieve products.
     * @return A ResponseEntity containing a list of ProductWrapper objects.
     */
    ResponseEntity<List<ProductWrapper>> getByCategory(Integer id);

    /**
     * Retrieves a specific product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return A ResponseEntity containing a single ProductWrapper object.
     */
    ResponseEntity<ProductWrapper> getProductById(Integer id);
}

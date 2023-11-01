package com.inn.cafe.dao;

import com.inn.cafe.POJO.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {
    /**
     * Retrieves a list of all products.
     *
     * @return A list of all product wrapper objects.
     */
    List<ProductWrapper> getAllProduct();

    /**
     * Updates the status of a product with the specified ID.
     *
     * @param status The new status to set for the product.
     * @param id The ID of the product to update.
     * @return The number of updated records (typically 1 if the update was successful).
     */
    @Modifying
    @Transactional
    Integer updateProductStatus(@Param("status") String status, @Param("id") Integer id);

    /**
     * Retrieves a list of product wrapper objects by category ID.
     *
     * @param id The ID of the category for which to retrieve products.
     * @return A list of product wrapper objects associated with the specified category.
     */
    List<ProductWrapper> getProductByCategory(@Param("id") Integer id);

    /**
     * Retrieves a product wrapper object by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The product wrapper object associated with the specified ID, or null if not found.
     */
    ProductWrapper getProductById(Integer id);
}

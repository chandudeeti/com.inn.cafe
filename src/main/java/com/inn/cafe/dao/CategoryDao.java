package com.inn.cafe.dao;

import com.inn.cafe.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {

    /**
     * Retrieves a list of all categories.
     *
     * @return A list of all category objects.
     */
    List<Category> getAllCategory();

}

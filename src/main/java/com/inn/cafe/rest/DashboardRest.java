package com.inn.cafe.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
/**
 * REST API interface for retrieving dashboard details.
 */
@RequestMapping(path = "/dashboard")
public interface DashboardRest {

    /**
     * Retrieves counts and other relevant details for the dashboard.
     *
     * @return A ResponseEntity containing a map of key-value pairs representing dashboard details.
     */
    @GetMapping(path = "/details")
    ResponseEntity<Map<String, Object>> getCount();
}


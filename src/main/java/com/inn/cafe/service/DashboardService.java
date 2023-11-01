package com.inn.cafe.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Service interface for retrieving dashboard details.
 */
public interface DashboardService {

    /**
     * Retrieves counts and other relevant details for the dashboard.
     *
     * @return A ResponseEntity containing a map of key-value pairs representing dashboard details.
     */
    ResponseEntity<Map<String, Object>> getCount();
}

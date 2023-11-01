package com.inn.cafe.restImpl;

import com.inn.cafe.rest.DashboardRest;
import com.inn.cafe.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Implementation of the DashboardRest interface for retrieving dashboard details via REST endpoints.
 */
@RestController
public class DashboardRestImpl implements DashboardRest {

    @Autowired
    DashboardService dashboardService;

    /**
     * Retrieves counts and other relevant details for the dashboard.
     *
     * @return A ResponseEntity containing a map of key-value pairs representing dashboard details.
     */
    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        return dashboardService.getCount();
    }
}

package com.techsavvy.errortracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class DashboardConfigController {

    @GetMapping("/error-tracker/config.json")
    public Map<String, String> getConfig() {
        // Fetches URL from your Aspect
        return Map.of("apiBaseUrl", ErrorTrackerAspect.getBackendUrl());
    }
}

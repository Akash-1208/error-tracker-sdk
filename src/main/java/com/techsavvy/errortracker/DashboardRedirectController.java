package com.techsavvy.errortracker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardRedirectController {

    @GetMapping("/error-tracker")
    public String forwardToDashboard() {
        return "forward:/error-tracker/index.html";
    }
}

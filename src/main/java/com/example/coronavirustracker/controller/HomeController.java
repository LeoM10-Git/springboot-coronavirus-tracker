package com.example.coronavirustracker.controller;


import com.example.coronavirustracker.model.LocationStats;
import com.example.coronavirustracker.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronavirusDataService coronavirusDataService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("stats", coronavirusDataService.getAllStats());
        model.addAttribute("totalCase", coronavirusDataService.getTotalCases());
        model.addAttribute("totalNewCase", coronavirusDataService.getTotalNewCases());
        return "home";
    }

}

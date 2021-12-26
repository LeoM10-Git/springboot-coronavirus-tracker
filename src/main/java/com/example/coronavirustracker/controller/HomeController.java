package com.example.coronavirustracker.controller;


import com.example.coronavirustracker.services.DataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private DataService dataService;

    public HomeController(DataService dataService) {
        this.dataService = dataService;
    }


    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("stats", dataService.getAllStats());
        model.addAttribute("totalCase", dataService.getTotalCases());
        model.addAttribute("totalNewCase", dataService.getTotalNewCases());
        return "home";
    }

    // add search bar
    @GetMapping("/search")
    public String search(@RequestParam("countryName") String theCountryName, Model model){
        model.addAttribute("stats",dataService.searchByCountry(theCountryName));
        model.addAttribute("totalCase", dataService.getTotalCases());
        model.addAttribute("totalNewCase", dataService.getTotalNewCases());
        return "home";
    }


}

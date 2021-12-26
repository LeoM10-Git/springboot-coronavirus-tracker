package com.example.coronavirustracker.services;

import com.example.coronavirustracker.model.LocationStats;

import java.util.List;

public interface DataService {
    public List<LocationStats> searchByCountry(String theCountryName);
    public List<LocationStats> getAllStats();
    public String getTotalCases();
    public String getTotalNewCases();
}

package com.example.coronavirustracker.services;


import com.example.coronavirustracker.entity.CoronavirusData;
import com.example.coronavirustracker.model.LocationStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CoronavirusDataService implements DataService{

    private CoronavirusData coronavirusData;

    @Autowired
    public CoronavirusDataService(CoronavirusData coronavirusData) {
        this.coronavirusData = coronavirusData;
    }

    @Override
    public List<LocationStats> getAllStats(){
        return coronavirusData.getAllStats();
    }

    @Override
    public String getTotalCases(){
        return coronavirusData.getTotalCases();
    }

    @Override
    public String getTotalNewCases(){
        return coronavirusData.getTotalNewCases();
    }

    @Override
    public List<LocationStats> searchByCountry(String theCountryName) {
            return coronavirusData.searchByCountry(theCountryName);
    }
}

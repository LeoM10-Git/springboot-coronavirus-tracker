package com.example.coronavirustracker.services;

import com.example.coronavirustracker.entity.CoronavirusData;
import com.example.coronavirustracker.model.LocationStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CoronavirusDataService {

    private CoronavirusData coronavirusData;

    @Autowired
    public CoronavirusDataService(CoronavirusData coronavirusData) {
        this.coronavirusData = coronavirusData;
    }

    public List<LocationStats> getAllStats(){
        return coronavirusData.getAllStats();
    }

    public String getTotalCases(){
        return coronavirusData.getTotalCases();
    }

    public String getTotalNewCases(){
        return coronavirusData.getTotalNewCases();
    }
}

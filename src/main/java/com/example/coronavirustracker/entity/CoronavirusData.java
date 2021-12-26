package com.example.coronavirustracker.entity;

import com.example.coronavirustracker.model.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.CharSequenceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CoronavirusData {
    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master" +
            "/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";


    @PostConstruct
    @Scheduled(cron = "@daily")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(httpResponse.body());

        // convert string to reader
        Reader reader = new CharSequenceReader(httpResponse.body());
        reader.close();

        // string reader
//        Reader reader = new StringReader(httpResponse.body());
//        reader.close();

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStats.setLatestTotalCases(latestCases);
            locationStats.setDiffFromPreviousDate(latestCases - prevDayCases);
//            System.out.println(locationStats);
            newStats.add(locationStats);
        }
        this.allStats = newStats;
    }

    public String getTotalCases() {
        DecimalFormat decimalFormat = new DecimalFormat("#, ###");
        int totalCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        return decimalFormat.format(totalCases);
    }

    public String getTotalNewCases() {
        DecimalFormat decimalFormat = new DecimalFormat("#, ###");
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDate()).sum();
        return decimalFormat.format(totalNewCases);
    }

    public List<LocationStats> searchByCountry(String theCountryName) {
        List<LocationStats> results = null;
        Map<String, List<LocationStats>> map = new HashMap<>();
        List<LocationStats> list = new ArrayList<>();
        List<String> country = new ArrayList<>();
        for (LocationStats stats : allStats){
            country.add(stats.getCountry().toLowerCase());
        }


        for (String countryName : country) {

            for (LocationStats stats : allStats) {
                if (countryName.equalsIgnoreCase(stats.getCountry())) {
                    list.add(stats);
                }
                map.put(countryName, list);
            }
            // reset the array list to empty
            list = new ArrayList<>();
        }


        if (theCountryName != null && (theCountryName.trim().length()>0)){
            if (country.contains(theCountryName.toLowerCase())){

                    results = map.get(theCountryName.toLowerCase());

            }
            else results = getAllStats();
        }else{
            results = getAllStats();
        }
        return results;
    }
}

package com.toth.matus;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toth.matus.models.CountryDataRecord;

import lombok.extern.java.Log;
import utils.AreaCalculator;
import utils.CoordinatesParser;

@Log
public class MainlandDeterminer {
    public static void main(String[] args) throws JsonProcessingException{
        // String csvFile = "mainland\\src\\main\\resources\\country-borders-test.csv";
        String csvFile = "mainland\\src\\main\\resources\\country-borders.csv";
        String line;
        HashMap<String, CountryDataRecord> countryDataMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                
                String id = parts[0];
                String code = parts[1];
                String name = parts[2];
                String rawCoordinates = parts[3];
                Optional<List<Point2D>> countryCoordinatesList = CoordinatesParser.parse(rawCoordinates);
                if(countryCoordinatesList.isEmpty()){
                    continue;
                }
                Double area = AreaCalculator.calculatePolygonArea(countryCoordinatesList.get());
                CountryDataRecord countryData = new CountryDataRecord(id, code, name, area);
                CountryDataRecord oldCountry = countryDataMap.get(name);
                if(oldCountry != null){
                    CountryDataRecord mainland = oldCountry.area() > countryData.area() ? oldCountry : countryData;
                    countryDataMap.put(name, mainland);
                }else{
                    countryDataMap.put(name, countryData);
                }
            }
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
        }
        List<String> recordIds = new ArrayList<>();
        for (CountryDataRecord countryDataRecord : countryDataMap.values()) {
            recordIds.add(countryDataRecord.id());
        }
        log.info(recordIds.toString());
  
    }
}
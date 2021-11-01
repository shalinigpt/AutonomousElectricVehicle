package com.example.vehicle.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.vehicle.model.*;

public class VehicleConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleConsumerService.class);

    @Autowired
    RestTemplate restTemplate;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getCurrentChargeLevel(String vin) {
        logger.info("getCurrentChargeLevel start : ");
        ResponseEntity<ElectricVehicle> result = null;

        final String baseUrl = "https://restmock.techgig.com/merc/charge_level";

        Map<String, String> params = new HashMap<String, String>();
        params.put("vin", vin);

//        ElectricVehicle vehicle = new ElectricVehicle(vin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map> request = new HttpEntity<>(params, headers);

        try {
            URI uri = new URI(baseUrl);
            result = restTemplate.postForEntity(uri, request, ElectricVehicle.class);
        } catch(HttpClientErrorException e1){
            String errorpayload = e1.getResponseBodyAsString();
            logger.error("error fetching getCurrentChargeLevel : "+ errorpayload);
        } catch(HttpStatusCodeException e2){
            String errorpayload = e2.getResponseBodyAsString();
            logger.error("error fetching getCurrentChargeLevel : "+ errorpayload);
        } catch (URISyntaxException e) {
            String errorMsg = e.getMessage();
            logger.error("error fetching getCurrentChargeLevel : "+ errorMsg);
            e.printStackTrace();
        }
        logger.info("getCurrentChargeLevel end : " + result.getBody().toString());
        if(result.getStatusCodeValue() == 200){
            return result.getBody().getCurrentChargeLevel();
        } else {
            return 0;
        }
    }

    public int getDistance(String source, String destination) {
        logger.info("getDistance start : ");
        ResponseEntity<ElectricVehicle> result = null;

        final String baseUrl = "https://restmock.techgig.com/merc/distance";

        Map<String, String> params = new HashMap<String, String>();
        params.put("source", source);
        params.put("destination", destination);

//        ElectricVehicle vehicle = new ElectricVehicle(vin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map> request = new HttpEntity<>(params, headers);

        try {
            URI uri = new URI(baseUrl);
            result = restTemplate.postForEntity(uri, request, ElectricVehicle.class);
        } catch(HttpClientErrorException e1){
            String errorpayload = e1.getResponseBodyAsString();
            logger.error("error fetching getDistance : "+ errorpayload);
        } catch(HttpStatusCodeException e2){
            String errorpayload = e2.getResponseBodyAsString();
            logger.error("error fetching getDistance : "+ errorpayload);
        } catch (URISyntaxException e) {
            String errorMsg = e.getMessage();
            logger.error("error fetching getDistance : "+ errorMsg);
            e.printStackTrace();
        }
        logger.info("getDistance end : " + result.getBody().toString());
        if(result.getStatusCodeValue() == 200){
            return result.getBody().getDistance();
        } else {
            return 0;
        }
    }

    public List<ChargingStation> getChargingStations(String source, String destination) {
        logger.info("getChargingStations start : ");
        ResponseEntity<ElectricVehicle> result = null;

        final String baseUrl = "https://restmock.techgig.com/merc/charging_stations";

        Map<String, String> params = new HashMap<String, String>();
        params.put("source", source);
        params.put("destination", destination);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map> request = new HttpEntity<>(params, headers);

        try {
            URI uri = new URI(baseUrl);
            result = restTemplate.postForEntity(uri, request, ElectricVehicle.class);
        } catch(HttpClientErrorException e1){
            String errorpayload = e1.getResponseBodyAsString();
            logger.error("error fetching getChargingStations : "+ errorpayload);
        } catch(HttpStatusCodeException e2){
            String errorpayload = e2.getResponseBodyAsString();
            logger.error("error fetching getChargingStations : "+ errorpayload);
        } catch (URISyntaxException e) {
            String errorMsg = e.getMessage();
            logger.error("error fetching getChargingStations : "+ errorMsg);
            e.printStackTrace();
        }
        logger.info("getChargingStations end : " + result.getBody().toString());
        if(result.getStatusCodeValue() == 200){
            return result.getBody().getChargingStations();
        } else {
            return null;
        }
    }


}

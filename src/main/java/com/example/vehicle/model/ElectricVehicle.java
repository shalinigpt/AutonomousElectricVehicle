package com.example.vehicle.model;

import java.util.*;

public class ElectricVehicle {

    private int transactionId;
    private boolean isChargingRequired;
    private String vin;
    private int currentChargeLevel;
    private String source;
    private String destination;
    private int distance;
    private List<ChargingStation> chargingStations = null;
    private List<ErrorDetails> errors;

    public ElectricVehicle(){}

    public ElectricVehicle(String vin) {
        this.vin = vin;
    }
    public ElectricVehicle(String vin, String source, String destination) {
        this.vin = vin;
        this.source = source;
        this.destination = destination;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId() {
        this.transactionId = new Random().nextInt(999999999);
    }

    public boolean isChargingRequired() {
        return isChargingRequired;
    }

    public void setChargingRequired(boolean chargingRequired) {
        isChargingRequired = chargingRequired;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getCurrentChargeLevel() {
        return currentChargeLevel;
    }

    public void setCurrentChargeLevel(int currentChargeLevel) {
        this.currentChargeLevel = currentChargeLevel;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<ChargingStation> getChargingStations() {
        return chargingStations;
    }

    public void setChargingStations(List<ChargingStation> chargingStations) {
        this.chargingStations = chargingStations;
    }

    public List<ErrorDetails> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetails> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ElectricVehicle{" +
                "transactionId=" + transactionId +
                ", isChargingRequired=" + isChargingRequired +
                ", vin='" + vin + '\'' +
                ", currentChargeLevel=" + currentChargeLevel +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", distance=" + distance +
                ", chargingStations=" + chargingStations +
                ", errors='" + errors + '\'' +
                '}';
    }
}
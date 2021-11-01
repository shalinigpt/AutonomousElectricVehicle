package com.example.vehicle.model;

public class ChargingStation {
    private String name;
    private int distance;
    private int limit;

    public ChargingStation(){}

    public ChargingStation(String name, int distance, int limit) {
        this.name = name;
        this.distance = distance;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "ChargingStation{" +
                "name='" + name + '\'' +
                ", distance='" + distance + '\'' +
                ", limit='" + limit + '\'' +
                '}';
    }
}
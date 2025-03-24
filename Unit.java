/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thecccjavaedition;

/**
 *
 * @author Rene Lorenzo Pitahin
 */
public class Unit {
    private String type;
    private String unitNumber;
    private String location;
    private String baseLocation;
    private boolean available;
    private int travelTime;
    private boolean returningToBase;

    public Unit(String type, String unitNumber, String location, String baseLocation) {
        this.type = type;
        this.unitNumber = unitNumber;
        this.location = location;
        this.baseLocation = baseLocation;
        this.available = true; // Default availability
        this.travelTime = 0;   // Default travel time
        this.returningToBase = false; // Default not returning to base
    }


    // Getters and setters
    public String getUnitNumber() {
        return unitNumber;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public boolean isReturningToBase() {
        return returningToBase;
    }

    public void setReturningToBase(boolean returningToBase) {
        this.returningToBase = returningToBase;
    }

    public String getBaseLocation() {
        return baseLocation;
    }
}


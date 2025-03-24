/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thecccjavaedition;

/**
 *
 * @author Rene Lorenzo Pitahin
 */
public class Incident {
    private static int counter = 1; // Static counter for unique incident numbers
    private final int incidentNumber;
    private String type;
    private String requirement;
    private String demand;
    private int timeLeft;
    private int solvingTime; // Time required to resolve the incident
    private boolean assigned;
    private boolean expired;
    private String assignedUnit;
    private String node;
    

    public Incident(String type, String requirement, String demand, int timeLeft, String node) {
        this.incidentNumber = counter++;
        this.type = type;
        this.requirement = requirement;
        this.demand = demand;
        this.timeLeft = timeLeft;
        this.node = node;
        this.assigned = false;
    }

    // Getters and setters
    public int getSolvingTime() {
        return solvingTime;
    }
    
    public int getIncidentNumber() {
        return incidentNumber;
    }

    public void setSolvingTime(int solvingTime) {
        this.solvingTime = solvingTime;
    }

    public String getType() {
        return type;
    }

    public String getRequirement() {
        return requirement;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
    
    public void decrementTimeLeft() {
        if (timeLeft > 0) {
            timeLeft--;
        }
    }
    
    public void decrementSolvingTime() {
        if (solvingTime > 0) {
            solvingTime--;
        }
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public String getAssignedUnit() {
        return assignedUnit;
    }

    public void setAssignedUnit(String assignedUnit) {
        this.assignedUnit = assignedUnit;
    }

    public String getNode() {
        return node;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
    
    
}







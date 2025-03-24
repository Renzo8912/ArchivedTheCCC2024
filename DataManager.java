/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thecccjavaedition;

/**
 *
 * @author Rene Lorenzo Pitahin
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Iterator;
import java.util.Random;
import java.util.*;
import javax.swing.SwingWorker;


public class DataManager {
    private final List<Unit> units;
    private final List<Incident> incidents;
    private final Graph cityGraph;
    private final Random random;
    private int resolvedCases = 0;
    private int unsolvedCases = 0;
    private CCCInterface ui;

    public DataManager(Graph cityGraph, CCCInterface ui) {
        this.cityGraph = cityGraph;
        this.ui = ui;
        units = new ArrayList<>();
        incidents = new ArrayList<>();
        random = new Random();
        initializeUnits();
        System.out.println("DataManager initialized with cityGraph.");
    }



    // Initialize units
    private void initializeUnits() {
    // Police units (base location: J4)
        units.add(new Unit("Police", "001", "J4", "J4"));
        units.add(new Unit("Police", "002", "J4", "J4"));
        units.add(new Unit("Police", "003", "J4", "J4"));
        units.add(new Unit("Police", "004", "J4", "J4"));
        units.add(new Unit("Police", "005", "J4", "J4"));

        // Ambulance units (base location: A1)
        units.add(new Unit("Ambulance", "041", "A1", "A1"));
        units.add(new Unit("Ambulance", "042", "A1", "A1"));
        units.add(new Unit("Ambulance", "043", "A1", "A1"));
        units.add(new Unit("Ambulance", "044", "A1", "A1"));
        units.add(new Unit("Ambulance", "045", "A1", "A1"));
        units.add(new Unit("Ambulance", "046", "A1", "A1"));

        // Fire truck units (base location: W2)
        units.add(new Unit("Fire Truck", "061", "W2", "W2"));
        units.add(new Unit("Fire Truck", "062", "W2", "W2"));
        units.add(new Unit("Fire Truck", "063", "W2", "W2"));
        units.add(new Unit("Fire Truck", "064", "W2", "W2"));
        units.add(new Unit("Fire Truck", "065", "W2", "W2"));
        units.add(new Unit("Fire Truck", "066", "W2", "W2"));
        units.add(new Unit("Fire Truck", "067", "W2", "W2"));
    }


    // Return a list of all units
    public List<Unit> getUnits() {
        return units;
    }

    // Return a list of all incidents
    public List<Incident> getIncidents() {
        return incidents;
    }
    
    // Assign a unit to an incident
    public String assignUnitToIncident(Graph graph, String unitNumber, String incidentType) {
        Unit unit = units.stream()
                .filter(u -> u.getUnitNumber().equals(unitNumber) && u.isAvailable())
                .findFirst()
                .orElse(null);

        Incident incident = incidents.stream()
                .filter(i -> i.getType().equals(incidentType))
                .findFirst()
                .orElse(null);

        if (unit == null) return "Unit is unavailable or does not exist.";
        if (incident == null) return "No matching incident found.";

        if (!unit.getType().equals(incident.getRequirement())) {
            return "Unit type does not match incident requirement.";
        }

        // Calculate travel time using Dijkstra or similar algorithm
        List<String> path = Dijkstra.shortestPath(graph, "Station", incidentType); // Replace with actual locations
        int travelTime = path.size() * 2; // Example travel time calculation

        unit.setAvailable(false);
        unit.setTravelTime(travelTime);
        incident.setAssigned(true);

        return "Unit " + unit.getUnitNumber() + " assigned to " + incident.getType() + " via path: " + path;
    }


    // Add a new incident
    public void addIncident(String type, String requirement, String demand, int timeLeft) {
        List<String> nodes = new ArrayList<>(cityGraph.getNodes());
        if (nodes.isEmpty()) {
            throw new IllegalStateException("Graph has no nodes.");
        }

        String randomNode = nodes.get(random.nextInt(nodes.size()));
        incidents.add(new Incident(type, requirement, demand, timeLeft, randomNode));
    }

    public Unit getUnitByNumber(String unitNumber) {
        return units.stream()
                .filter(u -> u.getUnitNumber().equals(unitNumber))
                .findFirst()
                .orElse(null);
    }




    // Get all available units as strings in the format "UnitNumber - UnitType"
    public String[] getAvailableUnits() {
        return units.stream()
                .filter(Unit::isAvailable)
                .map(unit -> unit.getUnitNumber() + " - " + unit.getType())
                .toArray(String[]::new);
    }

    // Get all unresolved incidents as strings (incident types)
    public String[] getUnresolvedIncidents() {
        return incidents.stream()
                .map(Incident::getType)
                .toArray(String[]::new);
    }

    // Dispatch a unit to an incident
    public String dispatchUnit(Graph graph, String unitNumber, String incidentNode) {
        Unit unit = units.stream()
                .filter(u -> u.getUnitNumber().equals(unitNumber) && u.isAvailable())
                .findFirst()
                .orElse(null);

        if (unit == null) {
            return "Unit is unavailable.";
        }

        Incident incident = incidents.stream()
                .filter(i -> i.getNode().equals(incidentNode) && !i.isAssigned())
                .findFirst()
                .orElse(null);

        if (incident == null) {
            return "Incident not found.";
        }

        List<String> path = Dijkstra.shortestPath(graph, unit.getLocation(), incidentNode);
        if (path.isEmpty()) {
            return "No valid path found.";
        }

        int travelTime = path.size() * 2; // Dynamic calculation
        unit.setTravelTime(travelTime);
        ui.simulateUnitMovement(unit, path, incident);

        return "Unit dispatched successfully.";
    }



    // Cancel a unit's dispatch
    public String cancelDispatch(String unitNumber) {
        Unit unit = units.stream()
                .filter(u -> u.getUnitNumber().equals(unitNumber) && !u.isAvailable())
                .findFirst()
                .orElse(null);

        if (unit == null) return "No active dispatch found for the unit.";

        unit.setAvailable(true);
        unit.setTravelTime(0); // Reset travel time
        return "Dispatch for Unit " + unit.getUnitNumber() + " has been canceled.";
    }


    // Update travel times for units and incident timers
    public void updateUnitsAndIncidents() {
        for (Unit unit : units) {
            if (unit.getTravelTime() > 0) {
                unit.setTravelTime(unit.getTravelTime() - 1);
            }
        }

        Iterator<Incident> iterator = incidents.iterator();
            while (iterator.hasNext()) {
                Incident incident = iterator.next();

                // Decrease the time left for unresolved incidents
                if (!incident.isAssigned() && incident.getTimeLeft() > 0) {
                    incident.decrementTimeLeft();
                }

                // Remove incidents with Time left = 0 and not assigned
                if (incident.getTimeLeft() <= 0 && !incident.isAssigned()) {
                    unsolvedCases++; // Increment unsolved cases
                    iterator.remove(); // Remove the incident
                }
            }

            // Update the UI to reflect changes
            ui.refreshIncidentTable();
            ui.refreshUnitTable();
        }



   private void returnUnitToBase(Unit unit) {
        List<String> returnPath = Dijkstra.shortestPath(cityGraph, unit.getLocation(), unit.getBaseLocation());
        unit.setTravelTime(returnPath.size() * 2); // Calculate return travel time
        ui.simulateUnitMovement(unit, returnPath, null); // Simulate return movement
    }

    public int getResolvedCases() {
        return resolvedCases;
    }

    public int getUnsolvedCases() {
        return unsolvedCases;
    }

    public void updateUnitLocation(String unitNumber, String newNode) {
        Unit unit = units.stream()
                .filter(u -> u.getUnitNumber().equals(unitNumber))
                .findFirst()
                .orElse(null);
        if (unit != null) {
            unit.setLocation(newNode); // Update to the new node
            ui.refreshUnitTable(); // Reflect changes in the UI
        }
    }
    
    public void markIncidentAsResolved(Incident incident) {
        if ("Solving".equals(incident.getDemand()) && incident.getSolvingTime() <= 0) {
            incidents.remove(incident); // Remove the resolved incident
            resolvedCases++; // Increment resolved cases
        }
    }



    // Helper to calculate return travel time
    private int calculateReturnTravelTime(Unit unit, Incident incident) {
        List<String> returnPath = Dijkstra.shortestPath(cityGraph, incident.getNode(), unit.getBaseLocation());
        return returnPath.size() * 2; // Example: 2 seconds per node
    }
    
    // Resolve incident after arrival
    private void resolveIncident(Unit unit, Incident incident) {
        javax.swing.Timer resolutionTimer = new javax.swing.Timer(1000, e -> {
            if (incident.getSolvingTime() > 0) {
                incident.decrementSolvingTime();

                // Debugging information
                System.out.println("Solving Incident: " + incident.getType() + ", Solving Time Left: " + incident.getSolvingTime());
                System.out.println("Unit: " + unit.getUnitNumber() + " is solving the incident.");

                // Update the UI to reflect solving time
                ui.refreshIncidentTable();
            } else {
                // Incident solved
                System.out.println("Incident solved. Unit " + unit.getUnitNumber() + " is now returning to base.");
                ((javax.swing.Timer) e.getSource()).stop(); // Stop the solving timer
                incidents.remove(incident); // Remove the resolved incident
                resolvedCases++;

                // Return the unit to base
                unit.setReturningToBase(true);
                unit.setTravelTime(calculateReturnTravelTime(unit, incident));
            }
        });

        resolutionTimer.start();
    }

    public String assignFollowUpIncident(String unitNumber, Incident nextIncident) {
        Unit unit = units.stream()
            .filter(u -> u.getUnitNumber().equals(unitNumber) && !u.isAvailable() && !u.isReturningToBase())
            .findFirst()
            .orElse(null);

        if (unit == null) {
            return "Unit is unavailable or already returning to base.";
        }

        if (!unit.getType().equals(nextIncident.getRequirement())) {
            return "Unit type does not match the incident requirement.";
        }

        // Calculate travel time to the next incident
        List<String> path = Dijkstra.shortestPath(cityGraph, unit.getBaseLocation(), nextIncident.getNode());
        if (path.isEmpty()) {
            return "No valid path found to the incident.";
        }

        // Assign the unit to the follow-up incident
        unit.setAvailable(false); // Ensure the unit remains busy
        unit.setTravelTime(path.size() * 2); // Set travel time to the new incident
        nextIncident.setAssigned(true);
        nextIncident.setAssignedUnit(unit.getUnitNumber()); // Track the assigned unit

        System.out.println("Follow-Up: Unit " + unit.getUnitNumber() + " assigned to new incident: " + nextIncident.getType());
        return "Unit " + unit.getUnitNumber() + " assigned to follow-up incident: " + nextIncident.getType();
    }


    
    

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thecccjavaedition;

/**
 *
 * @author Rene Lorenzo Pitahin
 */
import java.util.*;

public class Graph {
    private final Map<String, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(String from, String to, int weight) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(new Edge(to, weight));
    }

    public List<Edge> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    public Set<String> getNodes() {
        return adjacencyList.keySet();
    }

    public static class Edge {
        private final String to;
        private final int weight;

        public Edge(String to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        public String getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }
    }
}




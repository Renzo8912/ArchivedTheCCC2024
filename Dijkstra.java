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

public class Dijkstra {
    public static List<String> shortestPath(Graph graph, String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()) {
            String current = priorityQueue.poll();

            if (current.equals(end)) {
                break;
            }

            for (Graph.Edge neighbor : graph.getNeighbors(current)) {
                int newDist = distances.get(current) + neighbor.getWeight();
                if (newDist < distances.get(neighbor.getTo())) {
                    distances.put(neighbor.getTo(), newDist);
                    previousNodes.put(neighbor.getTo(), current);
                    priorityQueue.add(neighbor.getTo());
                }
            }
        }

        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previousNodes.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        if (!path.isEmpty() && !path.get(0).equals(start)) {
            path.clear();
        }

        return path;
    }
}


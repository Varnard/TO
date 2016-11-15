package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 04.10.2016.
 */
public class Result {

    private final List<Vertex> graph;
    private final List<Integer> distance;
    private final int total;

    public Result(List<Vertex> graph, List<Integer> distance) {
        this.graph = new ArrayList<>(graph);
        this.distance = new ArrayList<>(distance);
        total = distance.stream()
                .mapToInt(i -> i)
                .sum();
    }

    public Result(List<Vertex> graph) {
        this.graph = new ArrayList<>(graph);
        this.distance = new ArrayList<>();

        for (int i = 0; i < 49; i++) {
            distance.add(Distance.compute(graph.get(i), graph.get(i + 1)));
        }
        distance.add(Distance.compute(graph.get(0), graph.get(49)));

        total = distance.stream()
                .mapToInt(i -> i)
                .sum();
    }

    public List<Vertex> getGraph() {
        return graph;
    }

    public List<Integer> getDistance() {
        return distance;
    }

    public int getTotal() {
        return total;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nVertices:\n");
        for (Vertex v : graph) {
            sb.append(v.id);
            sb.append("->");
        }
        sb.append("\n\n");
        for (Vertex v : graph) {
            sb.append(v.id);
            sb.append("\t");
            sb.append(v.x);
            sb.append("\t");
            sb.append(v.y);
            sb.append("\n");
        }

        return sb.toString();
    }


}

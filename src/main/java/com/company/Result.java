package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 04.10.2016.
 */
public class Result {

    private final List<Vertex> graph;
    private final List<Integer> distance;
    private final int min;
    private final int max;
    private final int avg;
    private final int total;

    public Result(List<Vertex> graph, List<Integer> distance) {
        this.graph = new ArrayList<>(graph);
        this.distance = new ArrayList<>(distance);
        min=distance.stream()
                .mapToInt(i->i)
                .min()
                .getAsInt();
        max=distance.stream()
                .mapToInt(i->i)
                .max()
                .getAsInt();
        avg=new Double(distance.stream()
                .mapToInt(i->i)
                .average()
                .getAsDouble())
                .intValue();
        total=distance.stream()
                .mapToInt(i->i)
                .sum();
    }

    public List<Vertex> getGraph() {
        return graph;
    }

    public List<Integer> getDistance() {
        return distance;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getAvg() {
        return avg;
    }

    public int getTotal(){
        return total;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
//        sb.append("\nmin edge: " + min);
//        sb.append("\nmax edge: " + max);
//
//        sb.append("\naverage edge: "+ avg);
        sb.append("\nVertices:\n");
        for (Vertex v : graph){
            sb.append(v.id);
            sb.append("->");
//            sb.append("\t");
//            sb.append(v.x);
//            sb.append("\t");
//            sb.append(v.y);
//            sb.append("\n");
        }

        return sb.toString();
    }


}

package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        runAlgorithm(new NearestNeighbour());
//        runAlgorithm(new GreedyCycle());
//        runAlgorithm(new GRASPNN());
//        runAlgorithm(new GRASPGC());
        runAlgorithm(new RandomPath());
    }

    private static void runAlgorithm(Algorithm algorithm) {
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        int avg = 0;
        for (int i = 0; i < 100; i++) {
            Result r = algorithm.execute(vertices, vertices.get(i));
            r = LocalSearch.optimize(r, vertices);
            int path = r.getTotal();
            if (best == null) best = r;
            else if (path < best.getTotal()) best = r;

            if (max == null) max = path;
            else if (path > max) max = path;

            avg += path;
        }
        avg = (int) (avg / 100 + 0.5);
        System.out.println("\n\t" + algorithm.toString() + ":");
        System.out.println("Min: " + best.getTotal());
        System.out.println("Max: " + max);
        System.out.println("Avg: " + avg);
        System.out.println("Graph: \n----------" + best.toString() + "\n----------");
    }
}

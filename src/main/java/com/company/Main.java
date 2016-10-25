package com.company;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
//        runBasicAlgorithm(new RandomPath());
//        runBasicAlgorithm(new RandomPath());
//        runBasicAlgorithm(new RandomPath());
//        runBasicAlgorithm(new NearestNeighbour());
//        runBasicAlgorithm(new GreedyCycle());
//        runBasicAlgorithm(new GRASPNN());
//        runBasicAlgorithm(new GRASPGC());
//        runBasicAlgorithm(new RandomPath());
//        runMSLS();
        runILS();
    }

    private static void runBasicAlgorithm(Algorithm algorithm) {
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        int avg = 0;
        Long maxTime = null;
        Long bestTime = null;
        long avgTime = 0;
        long time = 0;

        for (int i = 0; i < 100; i++) {
            Result r = algorithm.execute(vertices, vertices.get(i));
            Instant before = Instant.now();
            r = LocalSearch.optimize(r, vertices);
            Instant after = Instant.now();
            time = Duration.between(before, after).toMillis();
            int path = r.getTotal();
            if (best == null) best = r;
            else if (path < best.getTotal()) best = r;

            if (max == null) max = path;
            else if (path > max) max = path;

            if (bestTime == null) bestTime = time;
            else if (time < bestTime) bestTime = time;

            if (maxTime == null) maxTime = time;
            else if (time > maxTime) maxTime = time;

            avg += path;
            avgTime += time;
        }

//        Vertex[] asdf=best.getGraph().toArray(new Vertex[50]);
//        int check=0;
//        for (int i = 0; i < 49; i++) {
//            check+=Distance.compute(asdf[i],asdf[i+1]);
//        }
//        check+=Distance.compute(asdf[49],asdf[0]);
        avg = (int) (avg / 100 + 0.5);
        avgTime = (avgTime / 100);
        System.out.println("\n\t" + algorithm.toString() + ":");
//        System.out.println("Check: "+ check);
        System.out.println("Min: " + best.getTotal());
        System.out.println("Max: " + max);
        System.out.println("Avg: " + avg);
        System.out.println("Min Time: " + bestTime);
        System.out.println("Max Time: " + maxTime);
        System.out.println("Avg Time: " + avgTime);
        System.out.println();
        System.out.println("Graph: \n----------" + best.toString() + "\n----------");
    }

    private static void runMSLS() {
        Algorithm algorithm = new RandomPath();
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        int avg = 0;
        Long maxTime = null;
        Long bestTime = null;
        long avgTime = 0;
        long time = 0;

        for (int j = 0; j < 10; j++) {
            Result localbest = null;
            Instant before = Instant.now();
            for (int i = 0; i < 1000; i++) {
                Result r = algorithm.execute(vertices, vertices.get(new Random().nextInt(100)));
                r = LocalSearch.optimize(r, vertices);
                int path = r.getTotal();
                if (localbest == null) localbest = r;
                else if (path < localbest.getTotal()) localbest = r;

            }
            int path = localbest.getTotal();
            if (best == null) best = localbest;
            else if (path < best.getTotal()) best = localbest;

            if (max == null) max = path;
            else if (path > max) max = path;

            avg += path;

            Instant after = Instant.now();
            time = Duration.between(before, after).toMillis();

            if (bestTime == null) bestTime = time;
            else if (time < bestTime) bestTime = time;

            if (maxTime == null) maxTime = time;
            else if (time > maxTime) maxTime = time;

            avgTime += time;
        }
//        Vertex[] asdf=best.getGraph().toArray(new Vertex[50]);
//        int check=0;
//        for (int i = 0; i < 49; i++) {
//            check+=Distance.compute(asdf[i],asdf[i+1]);
//        }
//        check+=Distance.compute(asdf[49],asdf[0]);
        avg = (int) (avg / 10 + 0.5);
        System.out.println("\n\t Multiple Start Local Search:");
//        System.out.println("Check: "+ check);
        System.out.println("Min: " + best.getTotal());
        System.out.println("Max: " + max);
        System.out.println("Avg: " + avg);
        System.out.println("Total Time: " + avgTime);
        avgTime = (avgTime / 10);
        System.out.println("Min Time: " + bestTime);
        System.out.println("Max Time: " + maxTime);
        System.out.println("Avg Time: " + avgTime);
        System.out.println();
        System.out.println("Graph: \n----------" + best.toString() + "\n----------");
    }

    private static void runILS() {
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        int avg = 0;
        for (int i = 0; i < 10; i++) {

            Result r = new RandomPath().execute(vertices, vertices.get(new Random().nextInt(50)));
            long time = 0;
            r = LocalSearch.optimize(r, vertices);

            Instant before = Instant.now();
            while (time < 30000) {
                Result r2 = LocalSearch.perturbate(r, vertices);
                r2 = LocalSearch.optimize(r2, vertices);
                Instant after = Instant.now();
                time = Duration.between(before, after).toMillis();
                if (r2.getTotal() < r.getTotal()) {
                    r = r2;
                }
            }
            int path = r.getTotal();
            if (best == null) best = r;
            else if (path < best.getTotal()) best = r;

            if (max == null) max = path;
            else if (path > max) max = path;

            avg += path;
        }

//        Vertex[] asdf=best.getGraph().toArray(new Vertex[50]);
//        int check=0;
//        for (int i = 0; i < 49; i++) {
//            check+=Distance.compute(asdf[i],asdf[i+1]);
//        }
//        check+=Distance.compute(asdf[49],asdf[0]);
        avg = (int) (avg / 10 + 0.5);
        System.out.println("\n\tIterated Local Search:");
//        System.out.println("Check: "+ check);
        System.out.println("Min: " + best.getTotal());
        System.out.println("Max: " + max);
        System.out.println("Avg: " + avg);
        System.out.println();
        System.out.println("Graph: \n----------" + best.toString() + "\n----------");
    }
}

package com.company;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Created by Asdfer on 25.10.2016.
 */
public class Basic {

    public static void run(Algorithm algorithm) {
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
}

package com.company;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

/**
 * Created by Asdfer on 25.10.2016.
 */
public class MSLS {

    public static void run() {
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
}

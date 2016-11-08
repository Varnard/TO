package com.company;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

/**
 * Created by Asdfer on 25.10.2016.
 */
public class ILS {

    public static void run() {
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        int avg = 0;
        for (int i = 0; i < 10; i++) {

            Result r = new RandomPath().execute(vertices, vertices.get(new Random().nextInt(100)));
            long time = 0;
            r = LocalSearch.optimize(r, vertices);

            Instant before = Instant.now();
            while (time < 2900) {
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

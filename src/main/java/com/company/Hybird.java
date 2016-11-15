package com.company;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Asdfer on 08.11.2016.
 */
//
//Hybrydowy algorytm genetyczny z pełną elitarnością (odpalić x10 razy, warunek stopu 1 uruchomienia:
// średni czas z 10 uruchomień MSLS)

public class Hybird {

    public static void run() {
        Algorithm algorithm = new RandomPath();
        List<Vertex> vertices = Parser.getVertices();
        List<Result> results = new ArrayList<>();
        Integer max = null;
        Result best = null;
        int avg = 0;


        for (int it = 0; it < 10; it++) {

            while (results.size() < 20) {
                Result r = algorithm.execute(vertices, vertices.get(new Random().nextInt(100)));
                r = LocalSearch.optimize(r, vertices);
                Result finalR = r;
                Optional m = results.stream()
                        .filter(res -> res.getTotal() == finalR.getTotal())
                        .findAny();
                if (!m.isPresent()) {
                    results.add(r);
                }
            }
            Random rand = new Random();

            long time = 0;
            Instant before = Instant.now();
            while (time < 2900) {
                int rand1 = rand.nextInt(20);
                int rand2 = rand.nextInt(20);
                while (rand2 == rand1) {
                    rand2 = rand.nextInt(20);
                }

                Result parent1 = results.get(rand1);
                Result parent2 = results.get(rand2);

                List<Vertex[]> similiarities = Analysis.getAllSimiliarities(parent1, parent2);
                List<Vertex> remainingVertices = new ArrayList<>(vertices);
                List<Vertex> simVertices = Analysis.getVertexSimiliarities(parent1, parent2)
                        .stream()
                        .map(v -> v[0])
                        .collect(Collectors.toList());

                for (int i = remainingVertices.size() - 1; i > -1; i--) {
                    int finalI = i;
                    if (simVertices.stream().filter(v -> v.id == remainingVertices.get(finalI).id).findAny().isPresent()) {
                        remainingVertices.remove(i);
                    }
                }

                List<Vertex> graph = new ArrayList<>();
                while (graph.size() != 50) {
                    if (!similiarities.isEmpty()) {
                        int i = new Random().nextInt(similiarities.size());

                        Vertex[] addition = similiarities.get(i);
                        similiarities.remove(i);

                        if (graph.size() + addition.length <= 50) {
                            for (Vertex v : addition) {
                                graph.add(v);
                            }
                        }
                    } else {
                        Vertex v = remainingVertices.get(new Random().nextInt(remainingVertices.size()));
                        remainingVertices.remove(v);
                        graph.add(v);
                    }
                }
                Result child = new Result(graph);
                child = LocalSearch.optimize(child, vertices);

                int longest = results.stream()
                        .mapToInt(r -> r.getTotal())
                        .max()
                        .getAsInt();

                if (child.getTotal() < longest) {
                    Result worst = results.stream()
                            .filter(r -> r.getTotal() == longest)
                            .findAny()
                            .get();

                    Result finalChild = child;
                    if (results.stream()
                            .filter(r -> r.getTotal() == finalChild.getTotal())
                            .findAny()
                            .isPresent()) {
                    } else {
                        results.remove(worst);
                        results.add(child);
                    }
                }
                Instant after = Instant.now();
                time = Duration.between(before, after).toMillis();
            }

            int min = results.stream()
                    .mapToInt(r -> r.getTotal())
                    .min()
                    .getAsInt();

            Result alpha = results.stream()
                    .filter(r -> r.getTotal() == min)
                    .findAny()
                    .get();

            int path = alpha.getTotal();
            if (best == null) best = alpha;
            else if (path < best.getTotal()) best = alpha;

            if (max == null) max = path;
            else if (path > max) max = path;
            avg += path;
        }


        avg = (int) (avg / 10 + 0.5);
        System.out.println("\n\tHybird:");
        System.out.println("Min: " + best.getTotal());
        System.out.println("Max: " + max);
        System.out.println("Avg: " + avg);
        System.out.println();
        System.out.println("Graph: \n----------" + best.toString() + "\n----------");
    }
}

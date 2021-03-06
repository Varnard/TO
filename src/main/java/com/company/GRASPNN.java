package com.company;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Asdfer on 11.10.2016.
 */
public class GRASPNN implements Algorithm {

    @Override
    public Result execute(List<Vertex> vertices, Vertex start){
        List<Vertex> verticesLeft = new ArrayList<>(vertices);

        List<Vertex> graph = new ArrayList<>();
        List<Integer> edges = new ArrayList<>();

        graph.add(start);
        verticesLeft.remove(start);

        Vertex current = start;

        for (int count=1;count<50;count++) {
            Map<Vertex, Integer> distances = new HashMap<>();
            for (Vertex v : verticesLeft) {
                if (!v.visited)
                    distances.put(v, Distance.compute(current, v));
            }
            List<Integer> rcl = distances.values().stream()
                    .mapToInt(i -> i)
                    .sorted()
                    .limit(3)
                    .boxed()
                    .collect(Collectors.toList());

            int randMinDist = rcl.get(new Random().nextInt(3));

            Map.Entry<Vertex, Integer> closest = distances.entrySet().stream()
                    .filter(entry -> entry.getValue() == randMinDist)
                    .findFirst()
                    .get();

            current = closest.getKey();
            verticesLeft.remove(current);
            graph.add(current);
            edges.add(closest.getValue());
        }
        edges.add(Distance.compute(start, graph.get(graph.size() - 1)));


        return new Result(graph,edges);
    }

    @Override
    public String toString() {
        return "Nearest Neighbour GRASP";
    }
}

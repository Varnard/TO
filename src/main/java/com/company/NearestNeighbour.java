package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Asdfer on 10.10.2016.
 */
public class NearestNeighbour implements Algorithm {

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
            int minDist = distances.values().stream()
                    .mapToInt(i -> i)
                    .min()
                    .getAsInt();

            Map.Entry<Vertex, Integer> closest = distances.entrySet().stream()
                    .filter(entry -> entry.getValue() == minDist)
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
        return "Nearest Neighbour";
    }
}

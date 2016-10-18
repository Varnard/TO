package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Asdfer on 11.10.2016.
 */
public class RandomPath implements Algorithm {
    @Override
    public Result execute(List<Vertex> vertices, Vertex start) {
        List<Vertex> verticesLeft = new ArrayList<>(vertices);

        List<Vertex> graph = new ArrayList<>();
        List<Integer> edges = new ArrayList<>();

        Random rand = new Random();

        graph.add(start);
        verticesLeft.remove(start);

        Vertex current = start;

        for (int count = 1; count < 50; count++) {
            Vertex newVertex = verticesLeft.get(rand.nextInt(verticesLeft.size()));
            verticesLeft.remove(newVertex);
            graph.add(newVertex);
            edges.add(Distance.compute(current, newVertex));

            current = newVertex;
        }
        edges.add(Distance.compute(start, graph.get(graph.size() - 1)));

        return new Result(graph, edges);
    }


    @Override
    public String toString() {
        return "Random Path";
    }
}

package com.company;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asdfer on 10.10.2016.
 */
public class GreedyCycle implements Algorithm {

    @Override
    public Result execute(List<Vertex> vertices, Vertex start){
        List<Vertex> verticesLeft = new ArrayList<>(vertices);

        List<Vertex> graph = new ArrayList<>();
        List<Integer> edges = new ArrayList<>();

        graph.add(start);
        verticesLeft.remove(start);

        Vertex current = start;

        for (int count=1;count<50;count++) {
            List<Integer[]> distances = new ArrayList<>();
            for (Vertex v : verticesLeft) {
                Integer[] candidate = new Integer[3];
                candidate[0]=v.id;
                candidate[1]=computeDistance(current,v);
                candidate[2]=computeDistance(start,v);
                distances.add(candidate);
            }

            int minAddedPath = distances.stream()
                    .mapToInt(i->(i[1]+i[2]))
                    .min()
                    .getAsInt();

            Integer[] bestDistances = distances.stream()
                    .filter(i->(i[1]+i[2])==minAddedPath)
                    .findAny()
                    .get();

            Vertex best = verticesLeft.stream()
                    .filter(v->v.id==bestDistances[0])
                    .findAny()
                    .get();


            current = best;
            verticesLeft.remove(current);
            graph.add(current);
            edges.add(bestDistances[1]);
        }
        edges.add(computeDistance(start,graph.get(graph.size()-1)));

        return new Result(graph,edges);

    }

    private int computeDistance(Vertex v1, Vertex v2){
        int dx = v1.x-v2.x;
        int dy = v1.y-v2.y;
        return new Double((Math.sqrt(dx*dx+dy*dy))+0.5).intValue();
    }
}

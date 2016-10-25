package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Asdfer on 11.10.2016.
 */
public class LocalSearch {

    public static Result optimize(Result original, List<Vertex> vertices) {
        Vertex[] graph = original.getGraph().toArray(new Vertex[50]);
        Integer[] edges = original.getDistance().toArray(new Integer[50]);

        List<Vertex> unusedVertices = new ArrayList<>(vertices);

        for (int i = 0; i < 50; i++) {
            unusedVertices.remove(graph[i]);
        }

        int bestVertexChange = 1;
        Vertex bestCandidate = null;
        int bestCandidateIndex = 0;
        int bestd1 = 0;
        int bestd2 = 0;
        int bestEdgeChange = 1;
        int[] bestIndex = new int[2];
        int[] bestDist = new int[2];

        while (bestEdgeChange > 0 || bestVertexChange > 0) {
            bestEdgeChange = 0;
            for (int i = 0; i < 49; i++) {
                for (int j = i + 1; j < 50; j++) {
                    int[] oldDist = new int[2];

                    if (i == 0) oldDist[0] = edges[49];
                    else oldDist[0] = edges[i - 1];
                    try {
                        oldDist[1] = edges[j];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    int[] newDist = new int[2];
                    if (i == 0 && j == 49) {
                        newDist[0] = Distance.compute(graph[i], graph[j]);
                        newDist[1] = newDist[0];

                    } else {
                        if (i == 0) newDist[0] = Distance.compute(graph[49], graph[j]);
                        else newDist[0] = Distance.compute(graph[i - 1], graph[j]);
                        if (j == 49) newDist[1] = Distance.compute(graph[i], graph[0]);
                        else newDist[1] = Distance.compute(graph[i], graph[j + 1]);
                    }

                    int change = (oldDist[0] + oldDist[1]) - (newDist[0] + newDist[1]);

                    if (change > bestEdgeChange) {
                        bestEdgeChange = change;
                        bestIndex[0] = i;
                        bestIndex[1] = j;
                        bestDist[0] = newDist[0];
                        bestDist[1] = newDist[1];
                    }
                }
            }

            bestVertexChange = 0;
            for (int i = 0; i < 50; i++) {
                for (Vertex v : unusedVertices) {
                    int nd1;
                    int nd2;
                    int d1;
                    int d2;
                    if (i == 0) {
                        nd1 = Distance.compute(v, graph[49]);
                        nd2 = Distance.compute(v, graph[1]);

                        d1 = edges[49];
                    } else if (i == 49) {
                        nd1 = Distance.compute(v, graph[48]);
                        nd2 = Distance.compute(v, graph[0]);

                        d1 = edges[48];
                    } else {
                        nd1 = Distance.compute(v, graph[i - 1]);
                        nd2 = Distance.compute(v, graph[i + 1]);

                        d1 = edges[i - 1];
                    }
                    d2 = edges[i];

                    int change = (d1 + d2) - (nd1 + nd2);

                    if (change > bestVertexChange) {
                        bestVertexChange = (d1 + d2) - (nd1 + nd2);
                        bestCandidate = v;
                        bestCandidateIndex = i;
                        bestd1 = nd1;
                        bestd2 = nd2;
                    }
                }
            }

            if (bestVertexChange > bestEdgeChange && bestVertexChange > 0) {
                unusedVertices.add(graph[bestCandidateIndex]);
                unusedVertices.remove(bestCandidate);
                graph[bestCandidateIndex] = bestCandidate;
                if (bestCandidateIndex == 0) edges[49] = bestd1;
                else edges[bestCandidateIndex - 1] = bestd1;
                edges[bestCandidateIndex] = bestd2;


            } else {
                if (bestEdgeChange > 0) {

                    int i = bestIndex[0];
                    int j = bestIndex[1];
                    graph = reverseGraph(graph, i, j);
                    edges = reverseEdges(edges, i, j);

                    edges[j] = bestDist[1];

                    if (i == 0) edges[49] = bestDist[0];
                    else edges[i - 1] = bestDist[0];

                }
            }
        }

        List<Vertex> finalGraph = Arrays.asList(graph);
        List<Integer> finalEdges = Arrays.asList(edges);

        return new Result(finalGraph, finalEdges);
    }

    public static Result perturbate(Result original, List<Vertex> vertices) {
        Vertex[] graph = original.getGraph().toArray(new Vertex[50]);
        Integer[] edges = original.getDistance().toArray(new Integer[50]);

        Random rand = new Random();
        List<Vertex> unusedVertices = new ArrayList<>(vertices);

        for (int i = 0; i < 50; i++) {
            unusedVertices.remove(graph[i]);
        }

        for (int j = 0; j < 2; j++) {
            Vertex v = unusedVertices.get(rand.nextInt(50));
            int i = rand.nextInt(50);
            int nd1;
            int nd2;
            if (i == 0) {
                nd1 = Distance.compute(v, graph[49]);
                nd2 = Distance.compute(v, graph[1]);
            } else if (i == 49) {
                nd1 = Distance.compute(v, graph[48]);
                nd2 = Distance.compute(v, graph[0]);
            } else {
                nd1 = Distance.compute(v, graph[i - 1]);
                nd2 = Distance.compute(v, graph[i + 1]);

                unusedVertices.add(graph[i]);
                unusedVertices.remove(v);
                graph[i] = v;
                if (i == 0) edges[49] = nd1;
                else edges[i - 1] = nd1;
                edges[i] = nd2;
            }
        }

        List<Vertex> finalGraph = Arrays.asList(graph);
        List<Integer> finalEdges = Arrays.asList(edges);

        return new Result(finalGraph, finalEdges);
    }


    private static Vertex[] reverseGraph(Vertex[] graph, int from, int to) {
        Vertex[] newGraph = new Vertex[50];
        for (int i = 0; i < from; i++) {
            newGraph[i] = graph[i];
        }
        for (int i = from, j = to; i <= to; i++, j--) {
            newGraph[i] = graph[j];
        }
        for (int i = to + 1; i < 50; i++) {
            newGraph[i] = graph[i];
        }
        return newGraph;
    }

    private static Integer[] reverseEdges(Integer[] edges, int from, int to) {
        Integer[] newEdges = new Integer[50];

        for (int i = 0; i < from; i++) {
            newEdges[i] = edges[i];
        }
        for (int i = to; i < 50; i++) {
            newEdges[i] = edges[i];
        }
        for (int i = from, j = to - 1; i <= to - 1; i++, j--) {

            if (j == -1) {
                newEdges[i] = edges[49];
            } else {
                newEdges[i] = edges[j];
            }
        }

        return newEdges;
    }

}
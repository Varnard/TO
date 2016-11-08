package com.company;

import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
//        Basic.run(new RandomPath());
//        Basic.run(new RandomPath());
//        Basic.run(new RandomPath());
//        Basic.run(new NearestNeighbour());
//        Basic.run(new GreedyCycle());
//        Basic.run(new GRASPNN());
//        Basic.run(new GRASPGC());
//        Basic.run(new RandomPath());
//        MSLS.run();
//        ILS.run();
//        analyzeVertices();
        analyzeEdges();
    }

    public static void analyzeVertices() {
        Algorithm algorithm = new RandomPath();
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        Result[] results = new Result[1000];
        int[] similiarity = new int[1000];
        int[] bestSimiliarity = new int[1000];

        for (int i = 0; i < 1000; i++) {
            Result r = algorithm.execute(vertices, vertices.get(new Random().nextInt(100)));
            r = LocalSearch.optimize(r, vertices);
            results[i] = r;
            int path = r.getTotal();
            if (best == null) best = r;
            else if (path < best.getTotal()) best = r;

        }
        for (int i = 0; i < 1000; i++) {
            for (int j = i + 1; j < 1000; j++) {
                int s = computeVertexSimiliarity(results[i], results[j]);
                similiarity[i] += s;
                similiarity[j] += s;
            }
        }
        for (int i = 0; i < 1000; i++) {
            if (!results[i].equals(best)) {
                bestSimiliarity[i] = computeVertexSimiliarity(best, results[i]);
            }
        }
        for (int i = 0; i < 1000; i++) {
            similiarity[i] /= 1000;
        }

        for (int i = 0; i < 1000; i++) {
            System.out.println(similiarity[i] + "\t" + bestSimiliarity[i] + "\t" + results[i].getTotal());
        }
    }

    public static void analyzeEdges() {
        Algorithm algorithm = new RandomPath();
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        Result[] results = new Result[1000];
        int[] similiarity = new int[1000];
        int[] bestSimiliarity = new int[1000];

        for (int i = 0; i < 1000; i++) {
            Result r = algorithm.execute(vertices, vertices.get(new Random().nextInt(100)));
            r = LocalSearch.optimize(r, vertices);
            results[i] = r;
            int path = r.getTotal();
            if (best == null) best = r;
            else if (path < best.getTotal()) best = r;

        }
        for (int i = 0; i < 1000; i++) {
            for (int j = i + 1; j < 1000; j++) {
                int s = computeEdgeSimiliarity(results[i], results[j]);
                similiarity[i] += s;
                similiarity[j] += s;
            }
        }
        for (int i = 0; i < 1000; i++) {
            if (!results[i].equals(best)) {
                bestSimiliarity[i] = computeEdgeSimiliarity(best, results[i]);
            }
        }
        for (int i = 0; i < 1000; i++) {
            similiarity[i] /= 1000;
        }

        for (int i = 0; i < 1000; i++) {
            System.out.println(similiarity[i] + "\t" + bestSimiliarity[i] + "\t" + results[i].getTotal());
        }
    }

    private static int computeVertexSimiliarity(Result r1, Result r2) {
        int similiarity = 0;
        Vertex[] g1 = r1.getGraph().toArray(new Vertex[50]);
        Vertex[] g2 = r2.getGraph().toArray(new Vertex[50]);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (g1[i].id == g2[j].id) {
                    similiarity += 1;
                }
            }
        }
        return similiarity;
    }

    private static int computeEdgeSimiliarity(Result r1, Result r2) {
        int similiarity = 0;
        Vertex[] g1 = r1.getGraph().toArray(new Vertex[50]);
        Vertex[] g2 = r2.getGraph().toArray(new Vertex[50]);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                int i2 = i + 1;
                int j2 = j + 1;
                if (i == 49) i2 = 0;
                if (j == 49) j2 = 0;
                if (g1[i].id == g2[j].id && g1[i2] == g2[j2]) {
                    similiarity += 1;
                }
            }
        }
        return similiarity;
    }
}

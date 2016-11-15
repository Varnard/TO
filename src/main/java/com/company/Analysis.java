package com.company;

import java.util.*;

/**
 * Created by Asdfer on 08.11.2016.
 */
public class Analysis {

    public static void analyzeVertices() {
        Algorithm algorithm = new RandomPath();
        List<Vertex> vertices = Parser.getVertices();
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

    public static List<Vertex[]> getVertexSimiliarities(Result r1, Result r2) {
        List<Vertex[]> similiarities = new ArrayList<>();
        Vertex[] g1 = r1.getGraph().toArray(new Vertex[50]);
        Vertex[] g2 = r2.getGraph().toArray(new Vertex[50]);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (g1[i].id == g2[j].id) {
                    Vertex[] same = {g1[i]};
                    similiarities.add(same);
                }
            }
        }
        return similiarities;
    }

    public static List<Vertex[]> getEdgeSimiliarities(Result r1, Result r2) {
        List<Vertex[]> similiaritiesRight = new ArrayList<>();
        List<Vertex[]> similiaritiesLeft = new ArrayList<>();
        Vertex[] g1 = r1.getGraph().toArray(new Vertex[50]);
        Vertex[] g2 = r2.getGraph().toArray(new Vertex[50]);
        for (int i = 0; i < 49; i++) {
            for (int j = 0; j < 49; j++) {
                int i2 = i + 1;
                int j2 = j + 1;
                if (g1[i].id == g2[j].id && g1[i2] == g2[j2]) {
                    Vertex[] edge = {g1[i], g1[i2]};
                    similiaritiesRight.add(edge);
                }
            }
        }


        for (int i = 0; i < 49; i++) {
            for (int j = 1; j < 50; j++) {
                int i2 = i + 1;
                int j2 = j - 1;
                if (g1[i].id == g2[j].id && g1[i2] == g2[j2]) {
                    Vertex[] edge = {g1[i], g1[i2]};
                    similiaritiesLeft.add(edge);
                }
            }
        }

        int rightSize = similiaritiesRight.size();
        if (rightSize > 1) {
            for (int i = rightSize - 1; i > 0; i--) {
                Vertex[] e1 = similiaritiesRight.get(i);
                Vertex[] e2 = similiaritiesRight.get(i - 1);
                if (e1[0] == e2[e2.length - 1]) {
                    similiaritiesRight.remove(i);
                    similiaritiesRight.remove(i - 1);
                    List<Vertex> temp = new ArrayList<>(Arrays.asList(e2));
                    for (int j = 1; j < e1.length; j++) {
                        temp.add(e1[j]);
                    }
                    similiaritiesRight.add(i - 1, temp.toArray(new Vertex[temp.size()]));
                }
            }
        }


        int leftSize = similiaritiesLeft.size();
        if (leftSize > 1) {
            for (int i = leftSize - 1; i > 0; i--) {
                Vertex[] e1 = similiaritiesLeft.get(i);
                Vertex[] e2 = similiaritiesLeft.get(i - 1);
                if (e1[0] == e2[e2.length - 1]) {
                    similiaritiesLeft.remove(i);
                    similiaritiesLeft.remove(i - 1);
                    List<Vertex> temp = new ArrayList<>(Arrays.asList(e2));
                    temp.add(e1[1]);
                    similiaritiesLeft.add(i - 1, temp.toArray(new Vertex[temp.size()]));
                }
            }
        }

        List<Vertex[]> similiarities = new ArrayList<>();
        similiarities.addAll(similiaritiesRight);
        similiarities.addAll(similiaritiesLeft);
        return similiarities;
    }

    public static List<Vertex[]> getAllSimiliarities(Result r1, Result r2) {
        List<Vertex[]> simVertices = getVertexSimiliarities(r1, r2);
        List<Vertex[]> simEdges = getEdgeSimiliarities(r1, r2);

        Set<Vertex[]> set = new HashSet<>();
        for (Vertex[] e : simEdges) {
            for (int i = 0; i < e.length; i++) {
                Vertex[] v = {e[i]};
                set.add(v);
            }
        }
        for (int i = simVertices.size() - 1; i > -1; i--) {
            int finalI = i;
            if (set.stream()
                    .filter(v -> v[0] == simVertices.get(finalI)[0])
                    .findAny()
                    .isPresent()) {
                simVertices.remove(i);
            }
        }

        List<Vertex[]> similiarities = new ArrayList<>();
        similiarities.addAll(simEdges);
        similiarities.addAll(simVertices);
        return similiarities;
    }
}

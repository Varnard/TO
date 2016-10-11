package com.company;

import java.util.*;

//wszystko zaokraglac matem.
public class Main {

    public static void main(String[] args) {
        testNN();
        testGC();
        testGRASPNN();
        testGRASPGC();
    }

    private static void testNN() {
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        int avg = 0;
        for (int i = 0; i < 100; i++) {
            Result r = new NearestNeighbour().execute(vertices, vertices.get(i));
            int path = r.getTotal();
            if (best == null) best = r;
            else if (path < best.getTotal()) best = r;

            if (max == null) max = path;
            else if (path > max) max = path;

            avg += path;
        }
        avg = (int) (avg / 100 + 0.5);
        System.out.println("\n\tNearest Neighbour:");
        System.out.println("Min: " + best.getTotal());
        System.out.println("Max: " + max);
        System.out.println("Avg: " + avg);
        System.out.println("Graph: \n[" + best.toString() + "]");
    }

    private static void testGC() {
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        int avg = 0;
        for (int i = 0; i < 100; i++) {
            Result r = new GreedyCycle().execute(vertices, vertices.get(i));
            int path = r.getTotal();
            if (best == null) best = r;
            else if (path < best.getTotal()) best = r;

            if (max == null) max = path;
            else if (path > max) max = path;

            avg += path;
        }
        avg = (int) (avg / 100 + 0.5);
        System.out.println("\n\tGreedy Cycle:");
        System.out.println("Min: " + best.getTotal());
        System.out.println("Max: " + max);
        System.out.println("Avg: " + avg);
        System.out.println("Graph: \n[" + best.toString() + "]");
    }

    private static void testGRASPNN() {
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        int avg = 0;
        for (int i = 0; i < 100; i++) {
            Result r = new GRASPNN().execute(vertices, vertices.get(i));
            int path = r.getTotal();
            if (best == null) best = r;
            else if (path < best.getTotal()) best = r;

            if (max == null) max = path;
            else if (path > max) max = path;

            avg += path;
        }
        avg = (int) (avg / 100 + 0.5);
        System.out.println("\n\tNearest Neighbour GRASP:");
        System.out.println("Min: " + best.getTotal());
        System.out.println("Max: " + max);
        System.out.println("Avg: " + avg);
        System.out.println("Graph: \n[" + best.toString() + "]");
    }

    private static void testGRASPGC() {
        List<Vertex> vertices = Parser.getVertices();
        Integer max = null;
        Result best = null;
        int avg = 0;
        for (int i = 0; i < 100; i++) {
            Result r = new GRASPGC().execute(vertices, vertices.get(i));
            int path = r.getTotal();
            if (best == null) best = r;
            else if (path < best.getTotal()) best = r;

            if (max == null) max = path;
            else if (path > max) max = path;

            avg += path;
        }
        avg = (int) (avg / 100 + 0.5);
        System.out.println("\n\tGreedy Cycle GRASP:");
        System.out.println("Min: " + best.getTotal());
        System.out.println("Max: " + max);
        System.out.println("Avg: " + avg);
        System.out.println("Graph: \n[" + best.toString() + "]");
    }
}

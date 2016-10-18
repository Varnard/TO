package com.company;

/**
 * Created by Asdfer on 18.10.2016.
 */
public class Distance {

    public static int compute(Vertex v1, Vertex v2) {
        int dx = v1.x - v2.x;
        int dy = v1.y - v2.y;
        return new Double((Math.sqrt(dx * dx + dy * dy)) + 0.5).intValue();
    }
}

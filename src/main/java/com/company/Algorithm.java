package com.company;

import java.util.List;

/**
 * Created by Asdfer on 10.10.2016.
 */
public interface Algorithm {

    Result execute(List<Vertex> vertices, Vertex start);
}

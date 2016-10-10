package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asdfer on 10.10.2016.
 */
public class Parser {

    public static List<Vertex> getVertices(){
        List<Vertex> vertices = new ArrayList<>();
        File file = new File(Parser.class.getResource("kroA100.tsp").getFile());
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line;
            int i =0;
            while ((line=br.readLine())!=null){
                i++;
                if (i<=6||i>106) continue;
                String[] sepLine;
                sepLine=line.split(" ");
                try {
                    vertices.add(new Vertex(Integer.parseInt(sepLine[1]), Integer.parseInt(sepLine[2]), i-7));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vertices;
    }
}

package org.nairu.elasticsearch.algorithm;

import org.nairu.elasticsearch.model.IDistance;
import org.nairu.elasticsearch.model.Point;

public class EuclideanDistance implements IDistance {

    @Override
    public double calculate(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }
}

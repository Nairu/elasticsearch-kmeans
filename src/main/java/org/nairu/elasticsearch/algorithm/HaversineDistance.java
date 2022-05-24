package org.nairu.elasticsearch.algorithm;

import org.nairu.elasticsearch.model.IDistance;
import org.nairu.elasticsearch.model.Point;

public class HaversineDistance implements IDistance {

    private static final double EARTH_RADIUS_M = 6371000;

    @Override
    public double calculate(Point p1, Point p2) {
        // The points have to be in degrees as they come in.
        double lon1 = Math.toRadians(p1.getX());
        double lat1 = Math.toRadians(p1.getY());
        double lon2 = Math.toRadians(p2.getX());
        double lat2 = Math.toRadians(p2.getY());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                 + Math.cos(lat1) * Math.cos(lat2)
                 * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        return c * EARTH_RADIUS_M;
    }
    
}

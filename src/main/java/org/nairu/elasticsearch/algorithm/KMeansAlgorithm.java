package org.nairu.elasticsearch.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.nairu.elasticsearch.model.Centroid;
import org.nairu.elasticsearch.model.IClusterAlgorithm;
import org.nairu.elasticsearch.model.IDistance;
import org.nairu.elasticsearch.model.Point;
import org.nairu.elasticsearch.model.Record;

public class KMeansAlgorithm implements IClusterAlgorithm {
    private final Random random;
    private final int maxIterations;
    private final int k;

    public KMeansAlgorithm(int maxIterations, int k) {
        this.random = new Random();
        this.maxIterations = maxIterations;
        this.k = k;
    }

    public KMeansAlgorithm(int maxIterations, Random random, int k) {
        this.random = random;
        this.maxIterations = maxIterations;
        this.k = k;
    }

    public List<Centroid> fit(List<Record> records, IDistance distance) {
        List<Centroid> centroids = getInitialCentroids(records, k);
        List<Centroid> lastState = new ArrayList<>();

        for (int i = 0; i < this.maxIterations; i++) {
            for (Record record : records) {
                calculateNearestCentroid(record, centroids, distance);
            }

            lastState = centroids;
            centroids = recalculateCentroidCenters(centroids);

            if (i == (this.maxIterations - 1) || centroids.equals(lastState)) {
                // We're finished, re-associate the documents.
                for (Record record : records) {
                    calculateNearestCentroid(record, centroids, distance);
                }
            }
        }

        return centroids;
    }

    private List<Centroid> recalculateCentroidCenters(List<Centroid> centroids) {
        return centroids.stream().map(KMeansAlgorithm::calculateNewCenter).collect(Collectors.toList());
    }

    // This is static so it can be used in the map above.
    private static Centroid calculateNewCenter(Centroid centroid) {
        if (centroid.getAssociatedRecords().isEmpty())
            return centroid;

        int avgX = 0;
        int avgY = 0;
        for (Record record : centroid.getAssociatedRecords()) {
            avgX += record.getLocation().getX();
            avgY += record.getLocation().getY();
        }

        int numRecords = centroid.getAssociatedRecords().size();

        return new Centroid(new Point(avgX / numRecords, avgY / numRecords));
    }

    private void calculateNearestCentroid(Record record, List<Centroid> centroids, IDistance distance) {
        double minimumDistance = Double.MAX_VALUE;
        Centroid nearest = null;

        for (Centroid centroid : centroids) {
            double currentDistance = distance.calculate(record.getLocation(), centroid.getLocation());
            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearest = centroid;
            }
        }

        if (nearest != null)
            nearest.getAssociatedRecords().add(record);
    }

    private List<Centroid> getInitialCentroids(List<Record> records, int k) {
        List<Centroid> centroids = new ArrayList<>();
        Point minPoint = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        Point maxPoint = new Point(Double.MIN_VALUE, Double.MIN_VALUE);

        // First, calculate the min / maxes. This will set the bounds for our random starting positions.
        for (Record record : records) {
            if (minPoint.getX() > record.getLocation().getX())
                minPoint.setX(record.getLocation().getX());
            else if (maxPoint.getX() < record.getLocation().getX())
                maxPoint.setX(record.getLocation().getX());
    
            if (minPoint.getY() > record.getLocation().getY())
                minPoint.setY(record.getLocation().getY());
            else if (maxPoint.getY() < record.getLocation().getY())
                maxPoint.setY(record.getLocation().getY());
        }

        System.out.println(String.format("Bounds: [%f,%f,%f,%f]", minPoint.getX(), minPoint.getY(), maxPoint.getX(), maxPoint.getY()));

        // Now lets make our random centroids.
        for (int i = 0; i < k; i++) {
            Centroid c = new Centroid(new Point(random.nextDouble() * (maxPoint.getX() - minPoint.getX()) + minPoint.getX(),
                                                 random.nextDouble() * (maxPoint.getY() - minPoint.getY()) + minPoint.getY()));
            System.out.println(String.format("InitCentroid[%f,%f]", c.getLocation().getX(), c.getLocation().getY()));
            centroids.add(c);
        }

        return centroids;
    }
}
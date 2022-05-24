package org.nairu.elasticsearch.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import org.nairu.elasticsearch.model.Centroid;
import org.nairu.elasticsearch.model.Point;
import org.nairu.elasticsearch.model.Record;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KMeansAlgorithmTest {
    @Test
    void testKMeans() {
        Random random = new Random(0);
        KMeansAlgorithm k = new KMeansAlgorithm(10, random);
        List<Record> records = calculateRandomRecords(100, random, 0, 0, 100, 100);
        List<Centroid> centroids = k.fit(records, 5, new EuclideanDistance());

        Assertions.assertEquals(5, centroids.size());
        for (Centroid centroid : centroids) {
            Assertions.assertTrue(!centroid.getAssociatedRecords().isEmpty());
        }
    }

    @Test
    void testKMeansLotsOfData() {
        Random random = new Random(0);
        KMeansAlgorithm k = new KMeansAlgorithm(10, random);
        List<Record> records = calculateRandomRecords(10000, random, 0, 0, 100, 100);
        List<Centroid> centroids = k.fit(records, 10, new EuclideanDistance());

        Assertions.assertEquals(10, centroids.size());
        for (Centroid centroid : centroids) {
            Assertions.assertTrue(!centroid.getAssociatedRecords().isEmpty());
        }
    }

    @Test
    void testKMeansNoData() {
        Random random = new Random(0);
        KMeansAlgorithm k = new KMeansAlgorithm(10, random);
        List<Record> records = new ArrayList<Record>();
        List<Centroid> centroids = k.fit(records, 10, new EuclideanDistance());
        Assertions.assertEquals(10, centroids.size());
        for (Centroid centroid : centroids) {
            Assertions.assertTrue(centroid.getAssociatedRecords().isEmpty());
        }
    }

    @Test
    void testKMeansAssociatesAllToOneClusterWhenKis1() {
        Random random = new Random(0);
        KMeansAlgorithm k = new KMeansAlgorithm(10, random);
        List<Record> records = calculateRandomRecords(10000, random, 0, 0, 100, 100);
        List<Centroid> centroids = k.fit(records, 1, new EuclideanDistance());
        Assertions.assertEquals(1, centroids.size());
        for (Centroid centroid : centroids) {
            Assertions.assertEquals(records.size(), centroid.getAssociatedRecords().size());
        }
    }

    @Test
    void testKMeansHaversineDistance() {
        Random random = new Random(0);
        KMeansAlgorithm k = new KMeansAlgorithm(10, random);
        List<Record> records = calculateRandomRecords(10000, random, -180, -90, 180, 90);
        List<Centroid> centroids = k.fit(records, 10, new HaversineDistance());
        Assertions.assertEquals(10, centroids.size());
    }

    @Test
    void testKMeansHaversineDistanceFromSeeds() {
        Random random = new Random(0);
        KMeansAlgorithm k = new KMeansAlgorithm(10, random);
        List<Point> seeds = Arrays.asList(new Point(-0.11, 51.509865),
                new Point(-1.89857, 52.489471),
                new Point(-3.200833, 55.948612),
                new Point(-1.080278, 53.958332));

        List<Record> records = calculateRandomRecordsFromSeedLocations(10000, 0.1, seeds, random);
        List<Centroid> centroids = k.fit(records, 4, new EuclideanDistance());
        Assertions.assertEquals(4, centroids.size());
        for (Centroid centroid : centroids) {
            System.out
                    .println(String.format("Centroid: location[%f, %f], numRecords[%d]", centroid.getLocation().getX(),
                            centroid.getLocation().getY(), centroid.getAssociatedRecords().size()));
        }
    }

    private List<Record> calculateRandomRecords(int numRecords, Random random, double minX, double minY, double maxX,
            double maxY) {
        List<Record> records = new ArrayList<Record>();

        for (int i = 0; i < numRecords; i++) {
            Record r = new Record(String.format("id-%d", i), getRandomPointInRange(minX, minY, maxX, maxY, random));
            records.add(r);
        }

        return records;
    }

    private List<Record> calculateRandomRecordsFromSeedLocations(int numRecords, double distance, List<Point> seeds,
            Random random) {
        List<Record> records = new ArrayList<Record>();

        for (int i = 0; i < numRecords; i++) {
            // Pick one of the seeds.
            Integer seed = random.nextInt(seeds.size());
            Point p = seeds.get(seed);
            Record r = new Record(String.format("id-%d", i), getRandomPointNearOtherPoint(p, distance, random));
            records.add(r);
        }
        return records;
    }

    private Point getRandomPointInRange(double minX, double minY, double maxX, double maxY, Random random) {
        return new Point(random.nextDouble() * (maxX - minX) + minX, random.nextDouble() * (maxY - minY) + minY);
    }

    private Point getRandomPointNearOtherPoint(Point seed, double distance, Random random) {
        double x = seed.getX() + (random.nextDouble() * (2 * distance) - distance);
        double y = seed.getY() + (random.nextDouble() * (2 * distance) - distance);
        return new Point(x, y);
    }
}

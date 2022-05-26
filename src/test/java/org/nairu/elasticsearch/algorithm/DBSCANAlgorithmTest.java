package org.nairu.elasticsearch.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.nairu.elasticsearch.model.Centroid;
import org.nairu.elasticsearch.model.Point;
import org.nairu.elasticsearch.model.Record;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class DBSCANAlgorithmTest {
    @Test
    public void testRecursiveFind() {
        Random random = new Random(0);

        DBSCANAlgorithm k = new DBSCANAlgorithm(10, 7, 100, 10);
        List<Point> seeds = Arrays.asList(new Point(5, 5),
            new Point(30, 25),
            new Point(30, 55),
            new Point(50, 5));

        List<Record> records = calculateRandomRecordsFromSeedLocations(10000, 7, seeds, random);
        List<Centroid> neighbours = k.fit(records, new EuclideanDistance());
        //List<Record> neighbours = k.findNeighboursNeighbours(records, records.get(0), new EuclideanDistance());
        System.out.println("Number of clusters: " + neighbours.size());
        if (neighbours.size() > 0) {
            System.out.println("Average cluster size: " + neighbours.stream().map(c -> c.getAssociatedRecords().size()).reduce(0, (a, b) -> a + b) / neighbours.size());
            System.out.println("Biggest cluster size: " + neighbours.stream().map(c -> c.getAssociatedRecords().size()).reduce(0, (a, b) -> a > b ? a : b));
            for (Centroid c : neighbours) {
                System.out.println(String.format("Centroid[%f,%f] - %d", c.getLocation().getX(), c.getLocation().getY(), c.getAssociatedRecords().size()));
            }
        }
        Assertions.assertEquals(seeds.size(), neighbours.size());
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

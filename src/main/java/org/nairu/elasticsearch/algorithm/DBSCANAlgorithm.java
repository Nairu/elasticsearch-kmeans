package org.nairu.elasticsearch.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nairu.elasticsearch.model.Centroid;
import org.nairu.elasticsearch.model.IClusterAlgorithm;
import org.nairu.elasticsearch.model.IDistance;
import org.nairu.elasticsearch.model.Record;

public class DBSCANAlgorithm implements IClusterAlgorithm {

    private final int maxIterations;
    private final double maxDistance;
    private final int minClusterSize;
    private final int minNeighbours;

    public DBSCANAlgorithm(int maxIterations, double maxDistance, int minClusterSize, int minNeighbours) {
        this.maxIterations = maxIterations;
        this.maxDistance = maxDistance;
        this.minClusterSize = minClusterSize;
        this.minNeighbours = minNeighbours;
    }

    @Override
    public List<Centroid> fit(List<Record> records, IDistance distance) {
        List<Record> nonVisitedRecords = new ArrayList<>(records);
        // Keeps a track of all of the point pairings.
        List<Centroid> clusters = new ArrayList<>();

        while (nonVisitedRecords.size() > 0) {
            List<Record> candidateCluster = findNeighboursNeighbours(nonVisitedRecords, nonVisitedRecords.remove(0), distance);
            if (candidateCluster.size() > minClusterSize) {
                Centroid c = new Centroid();
                c.setAssociatedRecords(candidateCluster);
                c = c.calculateNewCenter(true);
                clusters.add(c);
            }
            nonVisitedRecords.removeAll(candidateCluster);
        }

        return clusters;
    }

    public List<Record> findNeighboursNeighbours(List<Record> records, Record currentRecord, IDistance distance) {
        Set<Record> allNeighbours = new HashSet<>();
        List<Record> processQueue = new ArrayList<>(Arrays.asList(currentRecord));
        List<Record> reducibleList = new ArrayList<>(records);

        while (processQueue.size() > 0) {
            currentRecord = processQueue.remove(0);
            reducibleList.remove(currentRecord);
            List<Record> neighbours = findNeighbours(reducibleList, currentRecord, distance);

            if (neighbours.size() > minNeighbours) {
                processQueue.addAll(neighbours);
                allNeighbours.addAll(neighbours);
            }
            reducibleList.removeAll(neighbours);
        }

        return new ArrayList<>(allNeighbours);
    }

    public List<Record> findNeighbours(List<Record> records, Record currentRecord, IDistance distance) {
        List<Record> neighbours = new ArrayList<>();

        for (Record record : records) {
            if (distance.calculate(record.getLocation(), currentRecord.getLocation()) <= maxDistance)
                neighbours.add(record);
        }

        return neighbours;
    }
}

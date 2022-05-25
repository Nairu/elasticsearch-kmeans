package org.nairu.elasticsearch.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nairu.elasticsearch.model.Centroid;
import org.nairu.elasticsearch.model.IClusterAlgorithm;
import org.nairu.elasticsearch.model.IDistance;
import org.nairu.elasticsearch.model.Record;

public class DBSCANAlgorithm implements IClusterAlgorithm {

    private final int maxIterations;
    private final double maxDistance;
    private final int minClusterSize;

    public DBSCANAlgorithm(int maxIterations, double maxDistance, int minClusterSize) {
        this.maxIterations = maxIterations;
        this.maxDistance = maxDistance;
        this.minClusterSize = minClusterSize;
    }

    @Override
    public List<Centroid> fit(List<Record> records, IDistance distance) {
        List<Record> newPositions = records;
        // Keeps a track of all of the point pairings.
        Map<String, String> recordNeighbours = new HashMap<>();

        return null;
    }

}

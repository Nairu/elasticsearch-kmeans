package org.nairu.elasticsearch.model;

import java.util.List;

public interface IClusterAlgorithm {
    public List<Centroid> fit(List<Record> records, IDistance distance);
}

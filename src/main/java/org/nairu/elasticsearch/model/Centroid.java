package org.nairu.elasticsearch.model;

import java.util.ArrayList;
import java.util.List;

public class Centroid {
    private Point location;
    private List<Record> associatedRecords;

    public Centroid() {
    }

    public Centroid(Point location) {
        this.location = location;
    }

    public Centroid(Point location, List<Record> associatedRecords) {
        this.location = location;
        this.associatedRecords = associatedRecords;
    }

    public Point getLocation() {
        return location;
    }

    public List<Record> getAssociatedRecords() {
        if (associatedRecords == null) {
            associatedRecords = new ArrayList<Record>();
        }
        return associatedRecords;
    }

    public void setAssociatedRecords(List<Record> associatedRecords) {
        this.associatedRecords = associatedRecords;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}

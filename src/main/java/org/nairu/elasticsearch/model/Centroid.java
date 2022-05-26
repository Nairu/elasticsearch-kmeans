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

    // This is static so it can be used in the map above.
    public Centroid calculateNewCenter(boolean keepRecords) {
        if (getAssociatedRecords().isEmpty())
            return this;

        float avgX = 0;
        float avgY = 0;
        for (Record record : getAssociatedRecords()) {
            avgX += record.getLocation().getX();
            avgY += record.getLocation().getY();
        }

        int numRecords = getAssociatedRecords().size();

        return new Centroid(new Point(avgX / numRecords, avgY / numRecords), keepRecords ? getAssociatedRecords() : new ArrayList<>());
    }
}

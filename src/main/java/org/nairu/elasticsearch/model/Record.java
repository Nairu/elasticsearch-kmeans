package org.nairu.elasticsearch.model;

public class Record {
    private String id;
    private Point location;

    public Record() {
    }

    public Record(String id, Point location) {
        this.setId(id);
        this.setLocation(location);
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

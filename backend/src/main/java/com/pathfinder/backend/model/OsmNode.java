package com.pathfinder.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OsmNode {

    private long id;
    private double lat;
    private double lng;
    private double gCost, hCost, fCost;
    private boolean visited;
    private OsmNode parent;


    // Constructor
    public OsmNode(long id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.gCost = Double.MAX_VALUE;
        this.fCost = Double.MAX_VALUE;
        this.hCost = 0;
    }

    // methode distanceTo(OsmNode other) -> send the distance in other Node formule of haversine
    public double distanceTo(OsmNode other) {
        double phi1 = this.lat * Math.PI / 180; // phiStart in radians;
        double phi2 = other.lat * Math.PI / 180; // phiFinish in radians
        double phiTri = (this.lat - other.lat) * Math.PI / 180; // difference between phi1 and phi2
        double lambda = (this.lng- other.lng) * Math.PI / 180;

        double a = Math.sin(phiTri/2) * Math.sin(phiTri/2) + Math.cos(phi1) *
                   Math.cos(phi2) * Math.sin(lambda/2) * Math.sin(lambda/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return c * 6371e3; // return metres
    }

    // methode reset() -> reset all data's algorithm
    public void reset() {
        this.parent = null;
        this.visited = false;
        this.gCost = Double.MAX_VALUE;
        this.fCost = Double.MAX_VALUE;
        this.hCost = 0;
    }


    public void recalculateF() {
        this.fCost = this.gCost + this.hCost;
    }

}

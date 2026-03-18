package com.pathfinder.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OsmEdge {
    private OsmNode from;
    private OsmNode to;
    private double distance;
    private String roadName;
    private String roadType;
    private boolean oneWay;

    public OsmEdge(OsmNode from, OsmNode to, String roadName, String roadType, boolean oneWay) {
        this.from = from;
        this.to = to;
        this.oneWay = oneWay;
        this.roadType = roadType;
        this.roadName = roadName;

        this.distance = this.from.distanceTo(this.to);
    }

    public double getTravelTimeSeconds() {
        double speedMs = getDefaultSpeed(this.roadType) * 1000 / 3600;
        return  distance / speedMs;
    }


    private double getDefaultSpeed(String roadType) {
        switch (roadType) {
            case "residential" :
                return 30;
            case "primary" :
                return 80;
            case "autocade" :
                return 100;
            default:
                return 50;

        }
    }
}

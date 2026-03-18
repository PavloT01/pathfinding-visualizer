package com.pathfinder.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PathResult {
    private String algorithm;
    private List<double[]> path;
    private long timeMs;
    private int pathLength;
    private int nodesVisited;
    private double distanceMeters;
    private boolean pathFound;
    private List<double[]> visitedNodes;

    public PathResult(String algorithm, List<OsmNode> path,
                      List<OsmNode> visited, long timeMs, boolean found) {

        this.algorithm = algorithm;
        this.pathFound = found;
        this.timeMs = timeMs;

        this.path = new ArrayList<>();
        for(OsmNode node : path) {
            this.path.add(new double[]{node.getLat(), node.getLng()});
        }


        this.distanceMeters = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            this.distanceMeters += path.get(i).distanceTo(path.get(i+1));
        }

        this.pathLength = path.size();
        this.nodesVisited = visited.size();

        this.visitedNodes = new ArrayList<>();
        for(OsmNode node : visited) {
            this.visitedNodes.add(new double[]{node.getLat(), node.getLng()});
        }
    }
}

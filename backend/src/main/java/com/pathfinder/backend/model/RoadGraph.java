package com.pathfinder.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class RoadGraph {
    private Map<Long, OsmNode> nodes = new HashMap<>();
    private Map<Long, List<OsmEdge>> adjacencyList = new HashMap<>();


    public void addEdge(OsmEdge edge) {
        adjacencyList.get(edge.getFrom().getId()).add(edge);

        if (!edge.isOneWay()) {
            OsmEdge reverse = new OsmEdge(
                    edge.getTo(), edge.getFrom(),
                    edge.getRoadName(), edge.getRoadType(), false
            );
            adjacencyList.get(edge.getTo().getId()).add(reverse);
        }
    }

    public void addNode(OsmNode node) {
        nodes.putIfAbsent(node.getId(), node);
        adjacencyList.putIfAbsent(node.getId(), new ArrayList<>());
    }

    public void resetAll() {
        nodes.values().forEach(OsmNode::reset);
    }

    public OsmNode findNearestNode(double lat, double lng) {
        OsmNode nearest = null;
        double minDist = Double.MAX_VALUE;
        OsmNode click = new OsmNode(-1, lat, lng);

        for (OsmNode node : nodes.values()) {
            double dist = node.distanceTo(click);
            if (dist < minDist) {
                minDist = dist;
                nearest = node;
            }
        }
        return nearest;
    }


    public int getNodeCount() { return nodes.size(); }
    public int getEdgeCount() {
        return adjacencyList.values().stream()
                .mapToInt(List::size).sum();
    }

    public List<OsmEdge> getEdgesFrom(long nodeId) {
        return adjacencyList.getOrDefault(nodeId, Collections.emptyList());
    }
}

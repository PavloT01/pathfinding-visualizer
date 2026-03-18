package com.pathfinder.backend.service;

import com.pathfinder.backend.model.OsmEdge;
import com.pathfinder.backend.model.OsmNode;
import com.pathfinder.backend.model.PathResult;
import com.pathfinder.backend.model.RoadGraph;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AStarService {



    public PathResult solve(RoadGraph graph, OsmNode start, OsmNode end) {
        graph.resetAll();
        long startTime = System.nanoTime();
        List<OsmNode> visitedOrder = new ArrayList<>();
        boolean found = runAStar(graph, start, end, visitedOrder);
        long timeMs = (System.nanoTime() - startTime) / 1_000_000;
        List<OsmNode> path = found ? reconstructPath(end) : new ArrayList<>();
        return new PathResult("A*", path, visitedOrder, timeMs, found);
    }

    private boolean runAStar(RoadGraph graph, OsmNode start, OsmNode end, List<OsmNode> visitedOrder) {
        PriorityQueue<OsmNode> openSet = new PriorityQueue<>(
                Comparator.comparingDouble(OsmNode::getFCost)
        );
        Set<Long> closedSet = new HashSet<>();


        start.setGCost(0);
        start.setHCost(start.distanceTo(end) * 1.5);
        start.recalculateF();
        openSet.add(start);

        while (!openSet.isEmpty()) {
            OsmNode current = openSet.poll();
            if (closedSet.contains(current.getId())) continue;
            closedSet.add(current.getId());
            current.setVisited(true);
            visitedOrder.add(current);

            if (current.getId() == end.getId()) return true;


            for (OsmEdge edge : graph.getEdgesFrom(current.getId())) {
                OsmNode neighbor = edge.getTo();
                if (closedSet.contains(neighbor.getId())) continue;

                double newG = current.getGCost() + edge.getDistance();
                if (newG < neighbor.getGCost()) {
                    neighbor.setGCost(newG);
                    neighbor.setHCost(neighbor.distanceTo(end) * 1.5);
                    neighbor.recalculateF();
                    neighbor.setParent(current);
                    openSet.add(neighbor);
                }
            }
        }
        return false;
    }

    private List<OsmNode> reconstructPath(OsmNode end) {
        List<OsmNode> path = new ArrayList<>();
        OsmNode current = end;
        while (current != null) {
            path.add(current);
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }
}

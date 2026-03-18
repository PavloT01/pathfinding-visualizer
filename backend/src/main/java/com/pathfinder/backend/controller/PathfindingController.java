package com.pathfinder.backend.controller;


import com.pathfinder.backend.dto.SolveRequest;
import com.pathfinder.backend.model.OsmNode;
import com.pathfinder.backend.model.PathResult;
import com.pathfinder.backend.model.RoadGraph;
import com.pathfinder.backend.service.AStarService;
import com.pathfinder.backend.service.GraphCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pathfinding")
@CrossOrigin(origins = "*")
public class PathfindingController {
    @Autowired
    GraphCacheService graphCacheService;
    @Autowired
    AStarService aStarService;

    @PostMapping("/solve")
    public ResponseEntity<PathResult> solve(@RequestBody SolveRequest req) {
        RoadGraph graph = graphCacheService.getGraph();
        OsmNode start = graph.findNearestNode(req.getStartLat(), req.getStartLng());
        OsmNode end   = graph.findNearestNode(req.getEndLat(),   req.getEndLng());
        PathResult result = aStarService.solve(graph, start, end);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/graph-info")
    public ResponseEntity<Map<String, Object>> graphInfo() {
        RoadGraph graph = graphCacheService.getGraph();  // ← додай це
        Map<String, Object> info = new HashMap<>();
        info.put("nodeCount", graph.getNodeCount());
        info.put("edgeCount", graph.getEdgeCount());
        return ResponseEntity.ok(info);
    }
}

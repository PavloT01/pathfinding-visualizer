package com.pathfinder.backend.service;

import com.pathfinder.backend.model.RoadGraph;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class GraphCacheService {
    private RoadGraph cachedGraph = null;

    @Autowired
    private GraphBuilder graphBuilder;

    @Autowired
    private OsmDataService osmDataService;

    @PostConstruct
    public void init() throws Exception {
        System.out.println("Loading road graph from Overpass API...");
        String json = osmDataService.loadStrasbourg();
        cachedGraph = graphBuilder.build(json);
        System.out.println("Graph ready! Nodes: " + cachedGraph.getNodeCount());
    }

    public RoadGraph getGraph() {
        return cachedGraph;
    }
}
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

    @PostConstruct
    public void init() throws Exception {
        System.out.println("Loading road graph from file...");

        // Read the JSON file from the project root
        String json = new String(Files.readAllBytes(
                Path.of("strasbourg.json")
        ));

        System.out.println("File loaded: " + json.length() / 1024 + " KB");

        // Parse JSON and build the graph
        cachedGraph = graphBuilder.build(json);

        System.out.println("Graph ready!");
        System.out.println("  Nodes: " + cachedGraph.getNodeCount());
        System.out.println("  Edges: " + cachedGraph.getEdgeCount());
    }

    public RoadGraph getGraph() {
        return cachedGraph;
    }
}
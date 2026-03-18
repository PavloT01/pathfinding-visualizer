package com.pathfinder.backend;

import com.pathfinder.backend.model.OsmNode;
import com.pathfinder.backend.model.PathResult;
import com.pathfinder.backend.model.RoadGraph;
import com.pathfinder.backend.service.AStarService;
import com.pathfinder.backend.service.GraphBuilder;
import com.pathfinder.backend.service.OsmDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(BackendApplication.class, args);
	}
}
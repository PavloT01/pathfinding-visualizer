package com.pathfinder.backend.service;

import com.pathfinder.backend.model.OsmEdge;
import com.pathfinder.backend.model.OsmNode;
import com.pathfinder.backend.model.RoadGraph;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class GraphBuilder {

    public RoadGraph build(String osmJson) throws Exception {



        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(osmJson);
        JsonNode elements = root.get("elements");

        Map<Long, OsmNode> allNodes = new HashMap<>();

        for(JsonNode element : elements) {
            if("node".equals(element.get("type").asText())){

                long id  = element.get("id").asLong();
                double lat = element.get("lat").asDouble();
                double lon = element.get("lon").asDouble();
                OsmNode node = new OsmNode(id, lat, lon);
                allNodes.put(id, node);
            }
        }

        RoadGraph graph = new RoadGraph();

        for(JsonNode element : elements) {
            if(!"way".equals(element.get("type").asText())) continue;

            JsonNode tags = element.get("tags");
            if(tags == null) continue;

            String highway = tags.has("highway") ? tags.get("highway").asText() : "residential";
            String name    = tags.has("name")    ? tags.get("name").asText()    : "Unknown";
            boolean oneWay = tags.has("oneway") && "yes".equals(tags.get("oneway").asText());

            JsonNode wayNodes = element.get("nodes");
            if(wayNodes == null || wayNodes.size() < 2) continue;

            for(int i = 0; i < wayNodes.size() - 1; i++) {
                long fromId = wayNodes.get(i).asLong();
                long toId   = wayNodes.get(i + 1).asLong();

                OsmNode from = allNodes.get(fromId);
                OsmNode to   = allNodes.get(toId);

                if(from == null || to == null) continue;

                graph.addNode(from);
                graph.addNode(to);
                graph.addEdge(new OsmEdge(from, to, name, highway, oneWay));
            }
        }


        return graph;
    }

}

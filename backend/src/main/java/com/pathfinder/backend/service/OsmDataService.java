package com.pathfinder.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


@Service
public class OsmDataService {


    public String loadCity(double south, double west,
                           double north, double east) throws IOException, InterruptedException {






        String query = String.format(
                "[out:json][timeout:25];" +
                        "way[\"highway\"~\"primary|secondary|tertiary|residential\"]" +
                        "(%f,%f,%f,%f);" +
                        "(._;>;);" +
                        "out body;",
                south, west, north, east
        );

        String url = "https://overpass.kumi.systems/api/interpreter?data="
                + URLEncoder.encode(query, StandardCharsets.UTF_8);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "PathfinderApp/1.0")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );
        return response.body();
    }

    public String loadStrasbourg() throws IOException, InterruptedException{
        return loadCity(48.54, 7.70, 48.62, 7.82);
    }


}

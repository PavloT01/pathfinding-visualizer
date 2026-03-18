package com.pathfinder.backend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolveRequest {
    private String algorithm;
    private double endLat, endLng;
    private double startLat, startLng;
}

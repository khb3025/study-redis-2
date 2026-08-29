package com.example.redisintermediateproject.geospatial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddCafeRequestDto {
    private String name;
    private double longitude;
    private double latitude;
}


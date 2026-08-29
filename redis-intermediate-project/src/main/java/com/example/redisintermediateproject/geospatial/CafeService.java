package com.example.redisintermediateproject.geospatial;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoSearchCommandArgs;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import static org.springframework.data.redis.domain.geo.GeoReference.fromCoordinate;
@Service
@RequiredArgsConstructor
public class CafeService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String GEOSPATIAL_KEY = "cafe";
    
    public void addCafe(AddCafeRequestDto addCafeRequestDto) {
        redisTemplate
            .opsForGeo()
            .add(
                GEOSPATIAL_KEY,
                new Point(addCafeRequestDto.getLongitude(), addCafeRequestDto.getLatitude()),
                addCafeRequestDto.getName()
            );
    }

    public List<Object> findCafesNearby(double longitude, double latitude, double distanceKm) {
        GeoSearchCommandArgs args = GeoSearchCommandArgs
            .newGeoSearchArgs()
            .includeDistance()
            .sortAscending();

        return redisTemplate
            .opsForGeo()
            .search(
                GEOSPATIAL_KEY,
                fromCoordinate(new Point(longitude,latitude)),
                new Distance(distanceKm, Metrics.KILOMETERS),
                args
            ).getContent().stream()
            .map(geoResult -> new CafeDistance(
                geoResult.getContent().getName(),
                geoResult.getDistance().getValue()
            ))
            .collect(Collectors.toList());
    }
}

package com.ssafy.travelmaker.model.service;

import com.ssafy.travelmaker.model.LocationDto;
import com.ssafy.travelmaker.model.MainTripDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private static final int MAX_SEARCH_COUNT = 9;
    private static final double RADIUS_KM = 10.0;

    private final TripService tripService;

    public List<MainTripDto> buildTripList(LocationDto location) {
        if (Objects.isNull(location)) {
            return tripService.findAll()
                    .stream()
                    .limit(MAX_SEARCH_COUNT)
                    .collect(Collectors.toList());
        }

        return tripService.findAll().stream()
                .peek(mainTripDto -> mainTripDto.setDistance(calculateDistance(mainTripDto.getLatitude(), mainTripDto.getLongitude(),
                        location.getLatitude(), location.getLongitude())))
                .filter(mainTripDto -> mainTripDto.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(MainTripDto::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371;
        return earthRadius * Math.acos(
                Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}

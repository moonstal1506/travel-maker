package com.ssafy.travelmaker.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.travelmaker.model.MainTripDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripRedisTemplateService {
    private static final String CACHE_KEY = "TRIP";
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(MainTripDto tripDto) {
        System.out.println(tripDto);
        if (Objects.isNull(tripDto) || Objects.isNull(tripDto.getContentId())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY, String.valueOf(tripDto.getContentId()),
                    serializeTripDto(tripDto));
            log.info("[TripRedisTemplateService save success] {}", tripDto.getContentId());
        } catch (Exception e) {
            log.error("[TripRedisTemplateService save error] {}", e.getMessage());
        }
    }

    public List<MainTripDto> findAll() {
        List<MainTripDto> list = new ArrayList<>();
        try {
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                MainTripDto tripDto = deserializeTripDto(value);
                list.add(tripDto);
            }
            return list;

        } catch (Exception e) {
            log.error("[TripRedisTemplateService findAll error] {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[TripRedisTemplateService delete success] {}", id);
    }

    private String serializeTripDto(MainTripDto tripDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(tripDto);
    }

    private MainTripDto deserializeTripDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, MainTripDto.class);
    }
}

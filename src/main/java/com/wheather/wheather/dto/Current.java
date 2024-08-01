package com.wheather.wheather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Current(
        Integer temperature,
        List<String> weather_descriptions,
        @JsonProperty("wind_speed")
        Integer windSpeed,
        @JsonProperty("wind_degree")
        Integer windDegree,

        Integer humidity,
        @JsonProperty("cloudcover")
        Integer cloudCover,
        @JsonProperty("pressure")
        Integer pressure
) {
}

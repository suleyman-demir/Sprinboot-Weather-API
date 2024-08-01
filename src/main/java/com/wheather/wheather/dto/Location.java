package com.wheather.wheather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public record Location(
        String name,
        String country,
        String region,
        @JsonProperty("localtime")
        String localTime) {
}

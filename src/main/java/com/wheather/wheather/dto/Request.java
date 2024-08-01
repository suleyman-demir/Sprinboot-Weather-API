package com.wheather.wheather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Request(
        String type,
        String query,
        String language,
        String unit) {


}

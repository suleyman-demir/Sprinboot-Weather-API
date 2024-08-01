package com.wheather.wheather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(
        Request request,
        Location location,
        Current current

) { }
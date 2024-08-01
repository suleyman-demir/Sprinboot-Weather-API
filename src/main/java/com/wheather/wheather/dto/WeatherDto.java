package com.wheather.wheather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wheather.wheather.model.WeatherEntity;
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherDto(
        String cityName,
        String country,
        Integer temperature,
        Integer windSpeed,
        Integer windDegree,
        Integer pressure,
        Integer humidity,
        Integer cloudCover) {


      public static WeatherDto convert (WeatherEntity from){
            return new WeatherDto(from.getCityName(), from.getCountry(), from.getTemperature(), from.getWindSpeed(), from.getWindDegree(),from.getPressure(), from.getHumidity(),from.getCloudCover());

        }
}

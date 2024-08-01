package com.wheather.wheather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

@Entity
public class WeatherEntity{
    @Id
    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String requestedCityName;
    private String cityName;
    private String country;
    private Integer temperature;
    private LocalDateTime updatedTime;
    private LocalDateTime responseLocalTime;
    private Integer windSpeed;
    private Integer windDegree;
    private Integer humidity;
    private Integer cloudCover;
    private Integer pressure;


    public WeatherEntity (String id, String requestedCityName, String cityName, String country, Integer temperature, LocalDateTime updatedTime, LocalDateTime responseLocalTime, Integer windSpeed, Integer windDegree, Integer humidity, Integer cloudCover, Integer pressure) {
        this.id = id;
        this.requestedCityName = requestedCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updatedTime = updatedTime;
        this.responseLocalTime = responseLocalTime;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.humidity = humidity;
        this.cloudCover=cloudCover;
        this.pressure = pressure;

    }
    public WeatherEntity(String requestedCityName, String cityName, String country, Integer temperature, LocalDateTime updatedTime, LocalDateTime responseLocalTime, Integer windSpeed,Integer windDegree, Integer humidity, Integer cloudCover,Integer pressure) {
        this.id = "";
        this.requestedCityName = requestedCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updatedTime = updatedTime;
        this.responseLocalTime = responseLocalTime;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.humidity = humidity;
        this.cloudCover = cloudCover;
        this.pressure = pressure;
    }

    public WeatherEntity() {

    }


    public String getId() {
        return id;
    }

    public String getRequestedCityName() {
        return requestedCityName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public LocalDateTime getResponseLocalTime() {
        return responseLocalTime;
    }

    public Integer getWindSpeed() {
        return windSpeed;

    }
    public Integer getWindDegree() {
        return windDegree;
    }
    public Integer getHumidity() {
        return humidity;
    }
    public Integer getCloudCover() {
        return cloudCover;
    }
    public Integer getPressure() {
        return pressure;
    }

}

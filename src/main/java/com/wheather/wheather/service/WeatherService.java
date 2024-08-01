package com.wheather.wheather.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wheather.wheather.constants.Constants;
import com.wheather.wheather.dto.WeatherDto;
import com.wheather.wheather.dto.WeatherResponse;
import com.wheather.wheather.model.WeatherEntity;
import com.wheather.wheather.repository.WeatherRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"weathers"})
public class WeatherService {
    //https://api.weatherstack.com/current?access_key=52b82bc2de0d763250b8a28c8373dfed&query=london

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

//     private static final String API_URL = "http://api.weatherstack.com/current?access_key=52b82bc2de0d763250b8a28c8373dfed&query=";       tüm tanımlamaları application.yml da gerçekleştirdik api url ve api key artık yml den constantsla çekiliy
//   ek olarak .env dosyasına apikey i güvenli bir şekilde sakladık isteğe bağlı olarak sistem ortak değişkenlerine tanımlama yapılırsa oradan da çekilebeilir.

    private final ObjectMapper objectMapper = new ObjectMapper();


    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }


    @Cacheable(key = "#city")
    public WeatherDto getWeatherByCityName(String city) {
        logger.info("Requested City : "+city);


        Optional<WeatherEntity> weatherEntityOptional = weatherRepository.findFirstByRequestedCityNameOrderByUpdatedTimeDesc(city);

        return weatherEntityOptional.map(weather -> {
                    if (weather.getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30))) {
                        return WeatherDto.convert(getWeatherFromWeatherStack(city));
                    }
                    return WeatherDto.convert(weather);
                })
                .orElseGet(() -> WeatherDto.convert(getWeatherFromWeatherStack(city)));

    }


    private WeatherEntity getWeatherFromWeatherStack(String city) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getWeatherStackUrl(city), String.class);

        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
            return saveWeatherEntity(city, weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @CacheEvict(allEntries = true)
    @PostConstruct
    @Scheduled(fixedRateString = "1800000")
    public void clearCache() {
        logger.info("Cache cleared");
    }

    private String getWeatherStackUrl(String city) {

        return Constants.API_URL + Constants.ACCESS_KEY_PARAM + Constants.API_KEY + Constants.QUERY_KEY_PARAM + city;
    }


    private WeatherEntity saveWeatherEntity(String city, WeatherResponse weatherResponse) {
        if (weatherResponse.location() == null) {

            throw new IllegalArgumentException("Location data is missing in the WeatherResponse");
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            WeatherEntity weatherEntity = new WeatherEntity(city,
                    weatherResponse.location().name(),
                    weatherResponse.location().country(),
                    weatherResponse.current().temperature(),
                    LocalDateTime.now(),
                    LocalDateTime.parse(weatherResponse.location().localTime(), dateTimeFormatter),
                    weatherResponse.current().windSpeed(),
                    weatherResponse.current().windDegree(),
                    weatherResponse.current().humidity(),
                    weatherResponse.current().cloudCover(),
                    weatherResponse.current().pressure()

            );

            return weatherRepository.save(weatherEntity);
        }
    }

}

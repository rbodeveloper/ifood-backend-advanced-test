package com.ifood.ifood.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class OpenWeatherDTO implements Serializable {

    @JsonProperty("temp")
    private Double temperature;

    public Optional<Double> getTemperature() {
        return Optional.ofNullable(temperature);
    }
}

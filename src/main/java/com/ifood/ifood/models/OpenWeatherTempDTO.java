package com.ifood.ifood.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpenWeatherTempDTO implements Serializable {

    private Double temperature;
}

package com.ifood.ifood.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class MainDTO implements Serializable, CacheableEntity {
    private OpenWeatherDTO main;

    private boolean dirtyByFallback;

    public Optional<OpenWeatherDTO> getMain() {
        return Optional.ofNullable(main);
    }

    @Override
    public boolean isDirtyByFallback() {
        return dirtyByFallback;
    }
}

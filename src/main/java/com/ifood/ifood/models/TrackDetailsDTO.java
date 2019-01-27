package com.ifood.ifood.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class TrackDetailsDTO implements Serializable {
    private String name;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}

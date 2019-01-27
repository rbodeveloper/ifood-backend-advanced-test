package com.ifood.ifood.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class ItemDTO implements Serializable {
    @JsonProperty("id")
    private String playlistId;

    public Optional<String> getPlaylistId() {
        return Optional.ofNullable(playlistId);
    }
}

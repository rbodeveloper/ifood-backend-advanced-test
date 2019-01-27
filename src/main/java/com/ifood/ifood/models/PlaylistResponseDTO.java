package com.ifood.ifood.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class PlaylistResponseDTO implements Serializable, CacheableEntity {
    @JsonProperty("playlists")
    private PlayListItemDTO playListItemDTO;

    private boolean dirtyByFallback;

    public Optional<PlayListItemDTO> getPlaylistItemDTO() {
        return Optional.ofNullable(playListItemDTO);
    }

    @Override
    public boolean isDirtyByFallback() {
        return dirtyByFallback;
    }
}

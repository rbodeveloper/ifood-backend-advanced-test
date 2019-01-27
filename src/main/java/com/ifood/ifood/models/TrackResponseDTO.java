package com.ifood.ifood.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
public class TrackResponseDTO implements Serializable, CacheableEntity {
    private List<ItemTrackDTO> items;
    private boolean dirtByFallback;

    public Optional<List<ItemTrackDTO>> getItems() {
        return Optional.ofNullable(items);
    }

    @Override
    public boolean isDirtyByFallback() {
        return dirtByFallback;
    }
}

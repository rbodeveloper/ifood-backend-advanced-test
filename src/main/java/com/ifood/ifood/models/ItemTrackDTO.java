package com.ifood.ifood.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class ItemTrackDTO implements Serializable {
    private TrackDetailsDTO track;

    public Optional<TrackDetailsDTO> getTrack() {
        return Optional.ofNullable(track);
    }
}

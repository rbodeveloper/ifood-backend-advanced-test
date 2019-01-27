package com.ifood.ifood.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
public class PlayListItemDTO implements Serializable {
    private List<ItemDTO> items;

    public Optional<List<ItemDTO>> getItems() {
        return Optional.ofNullable(items);
    }
}

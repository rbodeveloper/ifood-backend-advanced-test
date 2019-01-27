package com.ifood.ifood.enums;

/**
 * Enum that will be evaluated using
 * @see com.ifood.ifood.predicates.SuggestionPredicate#isGoodSuggestion(Double)
 * Note: lowerTempBound and higherTempBound when null will be ignored on evaluate.
 *       spotifyPlaylistId must be a valid playlistId reference on Spotify API
 *       @see com.ifood.ifood.feignservices.SpotifyFeignClient
 */
public enum MusicGenre {
    PARTY(31d, null, "party"),
    POP(15d, 30d, "pop"),
    ROCK(10d, 14d, "rock"),
    CLASSICAL(null, 9d, "classical");

    MusicGenre(Double lowerBound, Double higherBound, String spotifyPlaylistId) {
        this.higherTempBound = higherBound;
        this.lowerTempBound = lowerBound;
        this.spotifyPlaylistId = spotifyPlaylistId;
    }

    private Double lowerTempBound;
    private Double higherTempBound;
    private String spotifyPlaylistId;

    public Double getLowerTempBound() {
        return lowerTempBound;
    }

    public Double getHigherTempBound() {
        return higherTempBound;
    }

    public String getSpotifyPlaylistId() {
        return spotifyPlaylistId;
    }
}

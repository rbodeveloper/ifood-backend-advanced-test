package com.ifood.ifood.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifood.ifood.components.Utils;
import com.ifood.ifood.models.*;
import com.ifood.ifood.exceptions.NotFoundCitytException;
import com.ifood.ifood.exceptions.NotFoundPlaylistsException;
import com.ifood.ifood.exceptions.UnexpectedException;
import com.ifood.ifood.feignservices.*;
import com.ifood.ifood.enums.MusicGenre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ifood.ifood.predicates.SuggestionPredicate.isGoodSuggestion;

@Service
@Slf4j
public class RecommendationService {

    @Autowired
    private OpenWeatherFeignClient weatherClient;

    @Autowired
    private SpotifyFeignClient spotifyClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Utils utils;

    /**
     * Method that will return a list of tracks based on a city temperature
     * @param city name of the city
     * @return List of tracks
     */
    public List<String> suggestionByCity(String city) throws NotFoundCitytException, NotFoundPlaylistsException, UnexpectedException {
        MainDTO openWeatherDTO = weatherClient.getWeather(city);
        Double temperature = utils.orDefault(openWeatherDTO, new MainDTO()).getMain().flatMap(OpenWeatherDTO::getTemperature)
                .orElseThrow(() -> new NotFoundCitytException(String.format("City %s was not found", city)));
        return getTracksBasedOnTemperature(temperature);
    }

    /**
     * Method that will return a list of tracks based on a geolocation temperature.
     * @param latitude
     * @param longitude
     * @return List of tracks
     */
    public List<String> suggestionByLatLong(Double latitude, Double longitude) throws NotFoundCitytException, NotFoundPlaylistsException, UnexpectedException {
        MainDTO openWeatherDTO = weatherClient.getWeather(latitude, longitude);
        Double temperature = utils.orDefault(openWeatherDTO, new MainDTO()).getMain().flatMap(OpenWeatherDTO::getTemperature)
                .orElseThrow(() -> new NotFoundCitytException(String.format("City with latitude %s and longitude was not found", latitude, longitude)));
        return getTracksBasedOnTemperature(temperature);
    }

    /**
     * Helper function that given a temperature test all music genres.
     * @param temperature
     * @return Optional<MusicGenre>
     */
    private Optional<MusicGenre> recommendGenre(Double temperature) {
        return Arrays.stream(MusicGenre.values()).filter(isGoodSuggestion(temperature)).findFirst();
    }

    /**
     * Given a temperature the function will select the best genre and use
     * the Spotify client to get a playlist and its track name.
     * @param temperature
     * @return List of tracks if some genre fits on temperature or empty list.
     */
    private List<String> getTracksBasedOnTemperature(Double temperature) throws NotFoundPlaylistsException, UnexpectedException {
        Optional<MusicGenre> musicGenre = recommendGenre(temperature);
        if (musicGenre.isPresent()) {
            log.info(String.format("For temperature %s the recommendation is %s", temperature, musicGenre.get().getSpotifyPlaylistId()));
            PlaylistResponseDTO playlistResponseDTO = spotifyClient.getPlayList(musicGenre.get().getSpotifyPlaylistId());
            String playlistId = playlistResponseDTO.getPlaylistItemDTO()
                    .flatMap(PlayListItemDTO::getItems)
                    .map(f -> f.get(0))
                    .flatMap(ItemDTO::getPlaylistId)
                    .orElseThrow(() -> new NotFoundPlaylistsException(
                            String.format("Playlists for category %s were not found", musicGenre.get().getSpotifyPlaylistId())));
            TrackResponseDTO trackResponseDTO = spotifyClient.getTracks(playlistId);
            return trackResponseDTO.getItems()
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(ItemTrackDTO::getTrack)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(TrackDetailsDTO::getName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

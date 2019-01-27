package com.ifood.ifood.services;

import com.ifood.ifood.components.Utils;
import com.ifood.ifood.configurations.MainConfiguration;
import com.ifood.ifood.models.*;
import com.ifood.ifood.exceptions.NotFoundCitytException;
import com.ifood.ifood.exceptions.NotFoundPlaylistsException;
import com.ifood.ifood.exceptions.UnexpectedException;
import com.ifood.ifood.feignservices.OpenWeatherFeignClient;
import com.ifood.ifood.feignservices.SpotifyFeignClient;
import com.ifood.ifood.enums.MusicGenre;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.ThreadContext.isEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { RecommendationService.class, MainConfiguration.class, Utils.class})
public class RecommendationServiceTest {

    public static String CITY = "city";
    public static Double CITY_TEMPERATURE = 20d;
    public static String PLAYLIST_ID = "playList";
    public static String TRACK = "track";
    public static double LAT = 1d;
    public static double LOG = 1d;

    @MockBean
    private OpenWeatherFeignClient weatherClient;

    @MockBean
    private SpotifyFeignClient spotifyClient;

    @Mock
    private MainDTO mainDTO;

    @Mock
    private OpenWeatherDTO openWeatherDTO;

    @Mock
    private PlaylistResponseDTO playlistResponseDTO;

    @Mock
    private PlayListItemDTO playListItemDTO;

    @Mock
    private ItemDTO itemDTO;

    @Mock
    private TrackResponseDTO trackResponseDTO;

    @Mock
    private ItemTrackDTO itemTrackDTO;

    @Mock
    private TrackDetailsDTO trackDetailsDTO;

    @Autowired
    private RecommendationService recommendationService;

    @Before
    public void setup() throws UnexpectedException {
        when(mainDTO.getMain()).thenReturn(Optional.of(openWeatherDTO));
        when(openWeatherDTO.getTemperature()).thenReturn(Optional.of(CITY_TEMPERATURE));
        when(spotifyClient.getPlayList(MusicGenre.POP.getSpotifyPlaylistId())).thenReturn(playlistResponseDTO);
        when(playlistResponseDTO.getPlaylistItemDTO()).thenReturn(Optional.of(playListItemDTO));
        when(playListItemDTO.getItems()).thenReturn(Optional.of(Arrays.asList(itemDTO)));
        when(itemDTO.getPlaylistId()).thenReturn(Optional.of(PLAYLIST_ID));
        when(spotifyClient.getTracks(PLAYLIST_ID)).thenReturn(trackResponseDTO);
        when(trackResponseDTO.getItems()).thenReturn(Optional.of(Arrays.asList(itemTrackDTO)));
        when(itemTrackDTO.getTrack()).thenReturn(Optional.of(trackDetailsDTO));
        when(trackDetailsDTO.getName()).thenReturn(Optional.of(TRACK));
    }

    @Test
    public void suggestionByCityShouldReturnListOfString() throws NotFoundCitytException, NotFoundPlaylistsException, UnexpectedException {
        when(weatherClient.getWeather(CITY)).thenReturn(mainDTO);
        List<String> tracks = recommendationService.suggestionByCity(CITY);
        assertThat(tracks, equalTo(Arrays.asList(TRACK)));
    }

    @Test(expected = NotFoundCitytException.class)
    public void nullWeatherShouldGiveAExceptionWhenGetByCity() throws NotFoundCitytException, NotFoundPlaylistsException, UnexpectedException {
        when(weatherClient.getWeather(CITY)).thenReturn(null);
        List<String> tracks = recommendationService.suggestionByCity(CITY);
    }

    @Test(expected = NotFoundPlaylistsException.class)
    public void nullPlaylistShouldGiveAExceptionWhenGetByCity() throws NotFoundCitytException, NotFoundPlaylistsException, UnexpectedException {
        when(weatherClient.getWeather(CITY)).thenReturn(mainDTO);
        when(playlistResponseDTO.getPlaylistItemDTO()).thenReturn(Optional.empty());
        List<String> tracks = recommendationService.suggestionByCity(CITY);
    }

    @Test
    public void nullTracksShouldGiveAExceptionWhenGetByCity() throws NotFoundCitytException, NotFoundPlaylistsException, UnexpectedException {
        when(weatherClient.getWeather(CITY)).thenReturn(mainDTO);
        when(trackResponseDTO.getItems()).thenReturn(Optional.empty());
        List<String> tracks = recommendationService.suggestionByCity(CITY);
        assertThat(tracks, is(empty()));
    }

    @Test
    public void suggestionByLatLongShouldReturnListOfString() throws NotFoundCitytException, NotFoundPlaylistsException, UnexpectedException {
        when(weatherClient.getWeather(LAT, LOG)).thenReturn(mainDTO);
        List<String> tracks = recommendationService.suggestionByLatLong(LAT, LOG);
        assertThat(tracks, equalTo(Arrays.asList(TRACK)));
    }

    @Test(expected = NotFoundCitytException.class)
    public void nullWeatherShouldGiveAExceptionWhenGetByGeolocation() throws NotFoundCitytException, NotFoundPlaylistsException, UnexpectedException {
        when(weatherClient.getWeather(CITY)).thenReturn(null);
        List<String> tracks = recommendationService.suggestionByLatLong(LAT, LOG);
    }
}
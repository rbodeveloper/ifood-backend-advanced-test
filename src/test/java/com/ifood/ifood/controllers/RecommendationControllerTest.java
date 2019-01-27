package com.ifood.ifood.controllers;

import com.ifood.ifood.feignservices.OpenWeatherFeignClient;
import com.ifood.ifood.feignservices.SpotifyFeignClient;
import com.ifood.ifood.feignservices.fallback.OpenWeatherClientFallback;
import com.ifood.ifood.feignservices.fallback.SpotifyClientFactoryFallback;
import com.ifood.ifood.services.RecommendationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = RecommendationController.class)
public class RecommendationControllerTest {
    public static String RECOMMENDATION_BY_CITY_ENDPOINT = "/recommendation/city";
    public static String RECOMMENDATION_BY_GEOLOCATION_ENDPOINT = "/recommendation/geolocation";
    public static String CITY = "city";
    public static String TRACK = "track";
    public static String LAT = "10";
    public static String LON = "10";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenWeatherFeignClient openWeatherFeignClient;

    @MockBean
    private SpotifyFeignClient spotifyFeignClient;

    @MockBean
    private RecommendationService recommendationService;

    @MockBean
    private OpenWeatherClientFallback openWeatherClientFallback;

    @MockBean
    private SpotifyClientFactoryFallback spotifyClientFactoryFallback;

    @Test
    public void cityEndpointShouldReturnAListOfStringTest() throws Exception {
        when(recommendationService.suggestionByCity(CITY)).thenReturn(Arrays.asList(TRACK));

        mockMvc.perform(MockMvcRequestBuilders.get(RECOMMENDATION_BY_CITY_ENDPOINT + "?city=" + CITY)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        is(Arrays.asList(TRACK))));
    }

    @Test
    public void emptyShouldBeAValidOption() throws Exception {
        when(recommendationService.suggestionByCity(CITY)).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get(RECOMMENDATION_BY_CITY_ENDPOINT + "?city=" + CITY)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        is(Arrays.asList())));
    }

    @Test
    public void geolocationEndpointShouldReturnAListOfStringTest() throws Exception {
        when(recommendationService.suggestionByLatLong(Double.valueOf(LAT), Double.valueOf(LON))).thenReturn(Arrays.asList(TRACK));

        mockMvc.perform(MockMvcRequestBuilders.get(RECOMMENDATION_BY_GEOLOCATION_ENDPOINT + "?lat=" + LAT + "&lon=" + LON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        is(Arrays.asList(TRACK))));
    }

    @Test
    public void setRecommendationByGeolocationEndpointmptyShouldBeAValidOption() throws Exception {
        when(recommendationService.suggestionByLatLong(Double.valueOf(LAT), Double.valueOf(LON))).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get(RECOMMENDATION_BY_GEOLOCATION_ENDPOINT + "?lat=" + LAT + "&lon=" + LON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        is(Arrays.asList())));
    }
}
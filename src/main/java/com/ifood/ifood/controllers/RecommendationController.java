package com.ifood.ifood.controllers;

import com.ifood.ifood.exceptions.NotFoundCitytException;
import com.ifood.ifood.exceptions.NotFoundPlaylistsException;
import com.ifood.ifood.exceptions.UnexpectedException;
import com.ifood.ifood.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @RequestMapping(path = "/recommendation/city")
    public ResponseEntity<List<String>> pick(@RequestParam(name = "city") String city) throws NotFoundCitytException, NotFoundPlaylistsException, UnexpectedException {
        return new ResponseEntity<>(recommendationService.suggestionByCity(city), HttpStatus.OK);
    }

    @RequestMapping(path = "/recommendation/geolocation")
    public ResponseEntity<List<String>> pick2(@RequestParam(name = "lat") Double lat,
                                             @RequestParam(name = "lon") Double lon) throws NotFoundCitytException, NotFoundPlaylistsException, UnexpectedException {
        return new ResponseEntity<>(recommendationService.suggestionByLatLong(lat, lon), HttpStatus.OK);
    }
}

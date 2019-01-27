package com.ifood.ifood.predicates;

import com.ifood.ifood.enums.MusicGenre;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class SuggestionPredicateTest {

    @Test
    public void isGoodSuggestion32TemperatureShouldRecommendPartyTest() {
        assertThat(SuggestionPredicate.isGoodSuggestion(32d).test(MusicGenre.PARTY), equalTo(true));
    }

    @Test
    public void isGoodSuggestion29TemperatureShouldRecommendPopTest() {
        assertThat(SuggestionPredicate.isGoodSuggestion(16d).test(MusicGenre.POP), equalTo(true));
    }

    @Test
    public void isGoodSuggestion12TemperatureShouldRecommendRockTest() {
        assertThat(SuggestionPredicate.isGoodSuggestion(12d).test(MusicGenre.ROCK), equalTo(true));
    }

    @Test
    public void isGoodSuggestion12TemperatureShouldRecommendPartyTest() {
        assertThat(SuggestionPredicate.isGoodSuggestion(8d).test(MusicGenre.CLASSICAL), equalTo(true));
    }

    @Test
    public void closedIntervalTest() {
        assertThat(SuggestionPredicate.isGoodSuggestion(31d).test(MusicGenre.PARTY), equalTo(true));
        assertThat(SuggestionPredicate.isGoodSuggestion(14d).test(MusicGenre.ROCK), equalTo(true));
    }

}
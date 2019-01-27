package com.ifood.ifood.predicates;

import com.ifood.ifood.enums.MusicGenre;

import java.util.Objects;
import java.util.function.Predicate;

public class SuggestionPredicate {

    /**
     * Predicate used to get the  best music genre given a temperature.
     * It will check if given temperature fit in the music genre range. Null range will be considered unbounded,
     * It is the temperature will not be considered.
     * @param temperature City temperature in celcius
     * @return the MusicGenre recommendation
     */
    public static Predicate<MusicGenre> isGoodSuggestion(Double temperature) {
        return p -> (Objects.isNull(p.getLowerTempBound()) || temperature >= p.getLowerTempBound()) &&
                (Objects.isNull(p.getHigherTempBound()) || temperature <= p.getHigherTempBound());
    }
}

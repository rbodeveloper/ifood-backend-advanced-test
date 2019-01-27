package com.ifood.ifood.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

/**
 * Helper methods
 */
@Component
public class Utils {

    @Autowired
    private Random random;

    /**
     * Generate a random number given a range
     * @param min the lower bound
     * @param max the higher bound
     * @return randomized number
     */
    public int generateRandomInRange(int min, int max) {
        return random.nextInt((max - min)) + min;
    }

    public <T> T orDefault(T object, T ifNull) {
        return Objects.nonNull(object) ? object : ifNull;
    }
}

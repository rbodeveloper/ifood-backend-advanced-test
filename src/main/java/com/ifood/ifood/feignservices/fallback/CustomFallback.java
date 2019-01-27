package com.ifood.ifood.feignservices.fallback;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class that will be used to check if feign throwable is a known error and
 * It shouldn't have a fallback strategy
 */
public abstract class CustomFallback {
    @Setter
    @Getter
    private Throwable feignThrowable;

    /**
     *
     * @return true if it is a known error, false otherwise.
     */
    protected abstract boolean isExpectedException();
}

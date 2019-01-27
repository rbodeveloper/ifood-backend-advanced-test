package com.ifood.ifood.models;

/**
 * Interface that must be implemented by classes that will be used as response of a feign client.
 * It is necessary when a entity is generated on the fallback and the value should not be cached, because the end user
 * would wait until the next round cache to get a real result.
 */
public interface CacheableEntity {
    /**
     * Invoke a method to check if the entity was created in a fallback. If true this entity will not be cached,
     * because it is not a integrity result.
     * @return boolean
     */
    boolean isDirtyByFallback();
}

package com.ifood.ifood.configurations;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create a HazelCast instance that will be used as cache manager.
 * It is a distributed cache service.
 * @see <a href="https://hazelcast.com/">Reference</a>
 */
@Configuration
@EnableCaching
public class CacheConfig {
    public static final String WEATHER_CACHE = "weather";
    public static final String SPOTIFY_CACHE = "spotify";
    public static final int ONE_HOUR = 60 * 60;
    public static final int ONE_DAY = 60 * 60 * 24;

    @Bean
    public Config hazelCastConfig() {
        return new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(
                        new MapConfig()
                                .setName(WEATHER_CACHE)
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(ONE_HOUR))
                .addMapConfig(
                        new MapConfig()
                                .setName(SPOTIFY_CACHE)
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(ONE_DAY));
    }
}

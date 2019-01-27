package com.ifood.ifood.feignservices.fallback;

import com.ifood.ifood.feignservices.SpotifyFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Used to control the fallback given its cause.
 */

@Slf4j
@Component
public class SpotifyClientFactoryFallback implements FallbackFactory<SpotifyFeignClient> {

    @Autowired
    private BeanFactory beanFactory;

    @Override
    public SpotifyFeignClient create(Throwable throwable) {
        SpotifyClientFallback spotifyClientFallback = beanFactory.getBean(SpotifyClientFallback.class);
        spotifyClientFallback.setFeignThrowable(throwable);
        log.error("Error on spotify API", throwable);
        return spotifyClientFallback;
    }
}

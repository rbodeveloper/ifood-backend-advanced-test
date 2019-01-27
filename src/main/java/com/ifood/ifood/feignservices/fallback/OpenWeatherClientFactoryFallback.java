package com.ifood.ifood.feignservices.fallback;

import com.ifood.ifood.feignservices.OpenWeatherFeignClient;
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
public class OpenWeatherClientFactoryFallback implements FallbackFactory<OpenWeatherFeignClient> {
    @Autowired
    private BeanFactory beanFactory;

    @Override
    public OpenWeatherFeignClient create(Throwable throwable) {
        OpenWeatherClientFallback openWeatherClientFallback = beanFactory.getBean(OpenWeatherClientFallback.class);
        openWeatherClientFallback.setFeignThrowable(throwable);
        log.error("Error on open weather API", throwable);
        return openWeatherClientFallback;
    }
}

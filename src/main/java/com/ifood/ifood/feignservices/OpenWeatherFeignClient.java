package com.ifood.ifood.feignservices;

import com.ifood.ifood.models.MainDTO;
import com.ifood.ifood.feignservices.fallback.OpenWeatherClientFactoryFallback;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.ifood.ifood.configurations.CacheConfig.WEATHER_CACHE;

/**
 * Client to the Open Weather API that will be implemented by feign.
 * @see <a href="https://openweathermap.org/api">API reference</a>
 */
@FeignClient(name = "openWeather", url = "${openweather.base-url}", fallbackFactory = OpenWeatherClientFactoryFallback.class)
public interface OpenWeatherFeignClient {

    String DEFAULT_PARAMS = "&units=metric&appid=${openweather.api-key}";

    @Cacheable(cacheNames = WEATHER_CACHE, unless = "#result.isDirtyByFallback()")
    @GetMapping(value = "/weather?q={city}" + DEFAULT_PARAMS)
    MainDTO getWeather(@PathVariable("city") String city);

    @Cacheable(cacheNames = WEATHER_CACHE, unless = "#result.isDirtyByFallback()")
    @GetMapping(value = "/weather?lat={lat}&lon={lon}" + DEFAULT_PARAMS)
    MainDTO getWeather(@PathVariable("lat") Double lat, @PathVariable("lon") Double lon);

}

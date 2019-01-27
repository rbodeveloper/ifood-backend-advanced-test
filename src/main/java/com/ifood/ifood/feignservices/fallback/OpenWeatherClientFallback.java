package com.ifood.ifood.feignservices.fallback;

import com.ifood.ifood.components.Utils;
import com.ifood.ifood.models.MainDTO;
import com.ifood.ifood.models.OpenWeatherDTO;
import com.ifood.ifood.feignservices.OpenWeatherFeignClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Class that will provide a fallback to the OpenWeather API
 * This class must have a prototype scope, because It is stateful.
 * This class uses the random temperature as fallback strategy.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OpenWeatherClientFallback extends CustomFallback implements OpenWeatherFeignClient {

    private static final int minTempRange = 1;
    private static final int maxTempRange = 80;

    @Autowired
    private Utils utils;

    @Override
    public MainDTO getWeather(String city) {
        return treatFeignException();
    }

    @Override
    public MainDTO getWeather(Double lat, Double lon) {
        return treatFeignException();
    }

    private MainDTO generateARandomResponse() {
        int temperature = utils.generateRandomInRange(minTempRange, maxTempRange);
        OpenWeatherDTO openWeatherDTO = new OpenWeatherDTO();
        openWeatherDTO.setTemperature((double) temperature);
        MainDTO mainDTO = new MainDTO();
        mainDTO.setDirtyByFallback(true);
        mainDTO.setMain(openWeatherDTO);
        return mainDTO;
    }

    private MainDTO treatFeignException() {
        if (isExpectedException()) {
            return new MainDTO();
        }
        return generateARandomResponse();
    }

    protected boolean isExpectedException() {
        return getFeignThrowable() instanceof FeignException && ((FeignException) getFeignThrowable()).status() == 404;
    }
}

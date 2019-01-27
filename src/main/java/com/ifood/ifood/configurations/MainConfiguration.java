package com.ifood.ifood.configurations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Random;

/**
 * General configuration.
 */
@Configuration
@EnableWebSecurity
public class MainConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Enable free access to all endpoints. Because we are an open service.
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
    }

    /**
     * Create a ObjectMapper class that will be managed by IOC container.
     * @see java.util.Random
     * @return ObjectMapper Instance
     */
    @Bean
    public ObjectMapper createObjectmapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    /**
     * Create a Random class that will be managed by IOC container.
     * @see java.util.Random
     * @return Random Instance
     */
    @Bean
    public Random createRandom() {
        return new Random();
    }
}

package com.ifood.ifood.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Second strategy of fallback.
 * If the third party service is offline , it will load a static response, which was got from a real service,
 * and packed together to the application, in order to keep the application working.
 */
@Component
public class StaticalResponseLoader {
    private static final String STATICAL_RESPONSE_BUNDLE = "classpath:statical-response";

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Read a file located on resource that will generated instances of specified type
     * @param path fiel location under resources/statical-response folder
     * @param responseObject class that will be serialized the file
     * @return Serialized <T> object
     * @throws IOException if the file location is wrong
     */
    public <T> T readResponse(String path, Class<T> responseObject) throws IOException {
        Resource resource = resourceLoader.getResource(String.format("%s/%s", STATICAL_RESPONSE_BUNDLE, path));
        return objectMapper.readValue(resource.getInputStream(), responseObject);
    }
}

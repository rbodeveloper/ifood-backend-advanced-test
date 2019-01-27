package com.ifood.ifood.feignservices.fallback;

import com.ifood.ifood.components.StaticalResponseLoader;
import com.ifood.ifood.models.PlaylistResponseDTO;
import com.ifood.ifood.models.TrackResponseDTO;
import com.ifood.ifood.exceptions.UnexpectedException;
import com.ifood.ifood.feignservices.SpotifyFeignClient;
import feign.FeignException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * Class that will provide a fallback to the OpenWeather API
 * This class must have a prototype scope, because It is stateful.
 * This class uses statical responses that will be loaded in runtime as fallback strategy.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SpotifyClientFallback extends CustomFallback implements SpotifyFeignClient {

    public static final String TRACKS_RESPONSE_FILE = "tracks.json";

    @Getter
    @Setter
    private Throwable feignThrowable;

    @Autowired
    private StaticalResponseLoader staticalResponseLoader;

    @Override
    public PlaylistResponseDTO getPlayList(String category) throws UnexpectedException {
        if (!isExpectedException()) {
            try {
                PlaylistResponseDTO playlistResponseDTO = staticalResponseLoader.readResponse(
                        String.format("%s.json", category), PlaylistResponseDTO.class);
                //in case of object mapper returns a null value
                playlistResponseDTO = Objects.nonNull(playlistResponseDTO) ? playlistResponseDTO : new PlaylistResponseDTO();
                playlistResponseDTO.setDirtyByFallback(true);
                return playlistResponseDTO;
            } catch (IOException e) {
                throw new UnexpectedException("Error on playlist fallback serialization");
            }
        } else {
            return new PlaylistResponseDTO();
        }
    }

    @Override
    public TrackResponseDTO getTracks(String playlistId) throws UnexpectedException {
        if (!isExpectedException()) {
            try {
                //TODO improve tracks fallback to be something more dynamic
                TrackResponseDTO trackResponseDTO = staticalResponseLoader.readResponse(
                        String.format("%s", TRACKS_RESPONSE_FILE), TrackResponseDTO.class);
                //in case of object mapper returns a null value
                trackResponseDTO = Objects.nonNull(trackResponseDTO) ? trackResponseDTO : new TrackResponseDTO();
                trackResponseDTO.setDirtByFallback(true);
                return trackResponseDTO;
            } catch (IOException e) {
                throw new UnexpectedException("Error on track fallback serialization");
            }
        } else {
            return new TrackResponseDTO();
        }
    }

    protected boolean isExpectedException() {
        return getFeignThrowable() instanceof FeignException && ((FeignException) getFeignThrowable()).status() == 404;
    }
}

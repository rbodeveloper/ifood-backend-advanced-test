package com.ifood.ifood.feignservices;

import com.ifood.ifood.configurations.SpotifyConfiguration;
import com.ifood.ifood.models.PlaylistResponseDTO;
import com.ifood.ifood.models.TrackResponseDTO;
import com.ifood.ifood.exceptions.UnexpectedException;
import com.ifood.ifood.feignservices.fallback.SpotifyClientFactoryFallback;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.ifood.ifood.configurations.CacheConfig.SPOTIFY_CACHE;

/**
 * Client to the Spotify API that will be implemented by feign.
 * @see <a href="https://developer.spotify.com/">API reference</a>
 */
@FeignClient(name = "spotifyClient", url = "${spotify.base-url}",configuration = SpotifyConfiguration.class, fallbackFactory = SpotifyClientFactoryFallback.class)
public interface SpotifyFeignClient {

    @Cacheable(cacheNames = SPOTIFY_CACHE, unless = "#result.isDirtyByFallback()")
    @GetMapping("/browse/categories/{category_id}/playlists")
    PlaylistResponseDTO getPlayList(@PathVariable("category_id") String category) throws UnexpectedException;

    @Cacheable(cacheNames = SPOTIFY_CACHE, unless = "#result.isDirtyByFallback()")
    @GetMapping("/playlists/{playlist_id}/tracks")
    TrackResponseDTO getTracks(@PathVariable("playlist_id") String playlistId) throws UnexpectedException;


}

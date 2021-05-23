package social.raider.server.place.facade;

import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import social.raider.server.place.model.Place;
import social.raider.server.place.model.request.PlaceFetchRequest;
import social.raider.server.place.service.GooglePlacesService;
import social.raider.server.place.service.PlaceCacheService;
import social.raider.server.place.validator.NearbyPlaceFetchRequestValidator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PlaceFetchFacade {
    private final GooglePlacesService googlePlacesService;
    private final NearbyPlaceFetchRequestValidator nearbyPlaceFetchRequestValidator;
    private final PlaceCacheService placeCacheService;

    public List<Place> queryPlaces(String queryString) {
        log.debug(">>> PlaceFetchFacade::queryPlaces [queryString: {}]", queryString);
        var places = googlePlacesService.queryPlaces(queryString);
        cachePlaces(places);
        return places;
    }

    public List<Place> fetchNearbyPlaces(PlaceFetchRequest request) {
        log.debug(">>> PlaceFetchFacade::fetchPlaces [request: {}]", request);
        nearbyPlaceFetchRequestValidator.validate(request);

        var places = googlePlacesService.fetchNearbyPlaces(request.getLatitude(), request.getLongitude(), request.getRadius());
        cachePlaces(places);
        return places;
    }

    public Place fetchPlaceById(String placeId) {
        log.debug(">>> PlaceFetchFacade::fetchPlaceById [placeId: {}]", placeId);
        Optional<Place> placeFromCache;
        try {
            placeFromCache = placeCacheService.fetchCachedPlace(placeId);
        } catch (Exception e) {
            placeFromCache = Optional.empty();
        }
        if(placeFromCache.isEmpty()) {
            var placeFromGoogle = googlePlacesService.getPlaceById(placeId);
            cachePlaces(List.of(placeFromGoogle));
            return placeFromGoogle;
        }
        return placeFromCache.get();
    }

    @Async
    public void cachePlaces(List<Place> places) {
        log.trace("Trying to call cache service to cache places. [places: {}]", places);
        try {
            placeCacheService.cacheAllPlaces(places);
        } catch (Exception e) {
            log.error("Cannot cache places. Check the stack trace!", e);
        }
    }
}

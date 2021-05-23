package social.raider.server.place.service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.LatLng;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import social.raider.server.place.exception.PlaceApiCommunicationException;
import social.raider.server.place.mapper.PlaceMapper;
import social.raider.server.place.model.Place;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GooglePlacesService {

    private final GeoApiContext geoApiContext;
    private final PlaceMapper placeMapper;

    public List<Place> queryPlaces(String query) {
        log.debug(">>> GooglePlacesService::fetchPlaces [query: {}]", query);
        var request = PlacesApi.textSearchQuery(geoApiContext, query);
        try {
            return placeMapper.mapGooglePlaceSearchResultToPlace(request.await().results);
        } catch (InterruptedException | IOException e) {
            log.error("Cannot communicate with google.", e);
            throw new PlaceApiCommunicationException("Cannot communicate with Google.", e);
        } catch (ApiException e) {
            throw mapApiExceptionToPlaceApiCommunicationException(e);
        }
    }

    public List<Place> fetchNearbyPlaces(Double latitude, Double longitude, Integer radius) {
        log.debug(">>> GooglePlacesService::queryNearbyPlaces [latitude: {}, longitude: {}, radius: {}]", latitude, longitude, radius);
        var request = PlacesApi.nearbySearchQuery(geoApiContext, new LatLng(latitude, longitude));
        request.radius(radius);
        try {
            return placeMapper.mapGooglePlaceSearchResultToPlace(request.await().results);
        } catch (InterruptedException | IOException e) {
            log.error("Cannot communicate with google.", e);
            throw new PlaceApiCommunicationException("Cannot communicate with Google.", e);
        } catch (ApiException e) {
            throw mapApiExceptionToPlaceApiCommunicationException(e);
        }
    }

    public Place getPlaceById(String placeId) {
        log.debug(">>> GooglePlacesService::getPlaceById [placeId: {}]", placeId);
        var request = PlacesApi
                .placeDetails(geoApiContext, placeId);
        try {
            return placeMapper.mapGooglePlaceDetailsResultToPlace(request.await());
        } catch (InterruptedException | IOException e) {
            log.error("Cannot communicate with google.", e);
            throw new PlaceApiCommunicationException("Cannot communicate with Google.", e);
        } catch (ApiException e) {
            throw mapApiExceptionToPlaceApiCommunicationException(e);
        }
    }

    private PlaceApiCommunicationException mapApiExceptionToPlaceApiCommunicationException(ApiException e) {
        if(e instanceof NotFoundException) {
            return new PlaceApiCommunicationException(e, HttpStatus.NOT_FOUND.value());
        } else {
            return new PlaceApiCommunicationException(e, HttpStatus.BAD_GATEWAY.value());
        }
    }
}

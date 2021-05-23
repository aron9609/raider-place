package social.raider.server.place.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import social.raider.server.place.exception.TechnicalException;
import social.raider.server.place.mapper.PlaceMapper;
import social.raider.server.place.model.Place;
import social.raider.server.place.persistence.repository.PlaceRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class PlaceCacheService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    public Optional<Place> fetchCachedPlace(String placeId) {
        log.debug("PlaceCacheService::fetchCachedPlace [placeId: {}]", placeId);
        try {
            return placeRepository.findById(placeId).map(placeMapper::mapPlaceHashToPlace);
        } catch (Exception e) {
            throw new TechnicalException("Cannot fetch cached place.", e);
        }
    }

    public void cachePlace(Place place) {
        log.debug("PlaceCacheService::cachePlace [place: {}]", place);
        try {
            placeRepository.save(placeMapper.mapPlaceToPlaceHash(place));
        } catch (Exception e) {
            throw new TechnicalException("Cannot save place to cache.", e);
        }
    }


    public void cacheAllPlaces(List<Place> places) {
        log.debug("PlaceCacheService::cachePlace [places: {}]", places);
        try {
            placeRepository.saveAll(placeMapper.mapPlaceToPlaceHash(places));
        } catch (Exception e) {
            throw new TechnicalException("Cannot save place to cache.", e);
        }
    }
}

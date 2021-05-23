package social.raider.server.place.mapper;

import com.google.maps.model.AddressType;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import social.raider.server.place.model.Place;
import social.raider.server.place.persistence.model.PlaceHash;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PlaceMapper {
    private static final List<String> ALLOWED_PLACE_TYPES = Arrays.stream(PlaceType.values())
            .map(PlaceType::toString)
            .collect(Collectors.toList());

    public List<Place> mapGooglePlaceSearchResultToPlace(PlacesSearchResult[] placesSearchResults) {
        log.debug(">>> PlaceMapper::mapGooglePlaceSearchResultListToPlaceList");
        return Arrays.stream(placesSearchResults)
                .filter(this::filterAllowedPlaceTypes)
                .map(this::mapGooglePlaceSearchResultToPlace)
                .collect(Collectors.toList());
    }

    public Place mapGooglePlaceSearchResultToPlace(PlacesSearchResult placesSearchResult) {
        log.trace(">>> PlaceMapper::mapGooglePlaceSearchResultToPlace");
        return Place.builder()
                .id(placesSearchResult.placeId)
                .name(placesSearchResult.name)
                .latitude(placesSearchResult.geometry.location.lat)
                .longitude(placesSearchResult.geometry.location.lng)
                .type(Arrays.stream(placesSearchResult.types)
                        .filter(ALLOWED_PLACE_TYPES::contains)
                        .findFirst().orElse(null))
                .icon(Optional.ofNullable(placesSearchResult.icon).map(URL::toString).orElse(null))
                .build();
    }

    public Place mapGooglePlaceDetailsResultToPlace(PlaceDetails placeDetails) {
        log.trace(">>> PlaceMapper::mapGooglePlaceSearchResultToPlace");
        return Place.builder()
                .id(placeDetails.placeId)
                .name(placeDetails.name)
                .latitude(placeDetails.geometry.location.lat)
                .longitude(placeDetails.geometry.location.lng)
                .type(Arrays.stream(placeDetails.types)
                        .map(AddressType::toString)
                        .filter(ALLOWED_PLACE_TYPES::contains)
                        .findFirst().orElse(null))
                .icon(Optional.ofNullable(placeDetails.icon).map(URL::toString).orElse(null))
                .build();
    }

    public List<PlaceHash> mapPlaceToPlaceHash(List<Place> places) {
        return places.stream().map(this::mapPlaceToPlaceHash).collect(Collectors.toList());
    }

    public PlaceHash mapPlaceToPlaceHash(Place place) {
        return PlaceHash.builder()
                .id(place.getId())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .icon(place.getIcon())
                .name(place.getName())
                .type(place.getType())
                .build();
    }

    public Place mapPlaceHashToPlace(PlaceHash placeHash) {
        return Place.builder()
                .id(placeHash.getId())
                .latitude(placeHash.getLatitude())
                .longitude(placeHash.getLongitude())
                .icon(placeHash.getIcon())
                .name(placeHash.getName())
                .type(placeHash.getType())
                .build();
    }

    private boolean filterAllowedPlaceTypes(PlacesSearchResult placesSearchResult) {
        return Arrays.stream(placesSearchResult.types).anyMatch(ALLOWED_PLACE_TYPES::contains);
    }
}

package social.raider.server.place.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import social.raider.server.place.exception.PlaceApiCommunicationException;
import social.raider.server.place.facade.PlaceFetchFacade;
import social.raider.server.place.model.Place;
import social.raider.server.place.model.request.PlaceFetchRequest;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/places")
@AllArgsConstructor
public class PlaceController {

    private final PlaceFetchFacade placeFetchFacade;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Place> queryPlaces(@RequestParam("q") String queryString) {
        log.info(">>> PlaceController::queryPlaces [queryString: {}]", queryString);
        try {
            return placeFetchFacade.queryPlaces(queryString);
        } catch (IllegalArgumentException e) {
            log.error("Bad request.", e);
            throw new ServerWebInputException(e.getMessage(), null, e);
        } catch (PlaceApiCommunicationException e) {
            log.error("Communication error.", e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, e.getMessage());
        } catch (Exception e) {
            log.error("Internal server error.", e);
            throw new ServerErrorException("An unexpected error occurred! Please check the logs.", e);
        }
    }

    @GetMapping(path = "/nearby" ,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Place> fetchNearbyPlaces(PlaceFetchRequest request) {
        log.info(">>> PlaceController::fetchPlaces [request: {}]", request);
        try {
            return placeFetchFacade.fetchNearbyPlaces(request);
        } catch (IllegalArgumentException e) {
            log.error("Bad request.", e);
            throw new ServerWebInputException(e.getMessage(), null, e);
        } catch (PlaceApiCommunicationException e) {
            log.error("Communication error.", e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, e.getMessage());
        } catch (Exception e) {
            log.error("Internal server error.", e);
            throw new ServerErrorException("An unexpected error occurred! Please check the logs.", e);
        }
    }

    @GetMapping(path = "/{placeId}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Place fetchPlaceById(@PathVariable String placeId) {
        log.info(">>> PlaceController::fetchPlaceById [placeId: {}]", placeId);
        try {
            return placeFetchFacade.fetchPlaceById(placeId);
        } catch (IllegalArgumentException e) {
            log.error("Bad request.", e);
            throw new ServerWebInputException(e.getMessage(), null, e);
        } catch (PlaceApiCommunicationException e) {
            log.error("Communication error.", e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, e.getMessage());
        } catch (Exception e) {
            log.error("Internal server error.", e);
            throw new ServerErrorException("An unexpected error occurred! Please check the logs.", e);
        }
    }
}

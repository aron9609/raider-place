package social.raider.server.place.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import social.raider.server.place.model.request.PlaceFetchRequest;

@Slf4j
@Component
public class NearbyPlaceFetchRequestValidator implements Validator<PlaceFetchRequest> {

    @Override
    public void validate(PlaceFetchRequest request) {
        log.debug(">>> NearbyPlaceFetchRequestValidator::validate [request: {}]", request);

        if (!ObjectUtils.anyNotNull(request.getLatitude(), request.getLongitude(), request.getRadius())
                && !ObjectUtils.allNotNull(request.getLatitude(), request.getLongitude(), request.getRadius())) {
            throw new IllegalArgumentException("The radius, lat and lon fields should be present together!");
        }
    }
}

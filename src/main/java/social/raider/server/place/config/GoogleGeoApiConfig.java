package social.raider.server.place.config;

import com.google.maps.GeoApiContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GoogleGeoApiConfig {

    @Value("${google.geo.api-key}")
    private String apiKey;

    @Bean
    public GeoApiContext geoApiContext() {
        log.info("Setting up Google geo api context.");
        return new GeoApiContext.Builder()
                .apiKey(apiKey)
                .maxRetries(3)
                .build();
    }
}

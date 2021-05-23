package social.raider.server.place.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "Place", timeToLive = 172800)
public class PlaceHash {

    @Id
    private String id;
    private String name;
    private String type;
    private Double latitude;
    private Double longitude;
    private String icon;
}

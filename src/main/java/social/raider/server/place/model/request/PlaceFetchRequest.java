package social.raider.server.place.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaceFetchRequest {

    private Double latitude;
    private Double longitude;
    private Integer radius;
}

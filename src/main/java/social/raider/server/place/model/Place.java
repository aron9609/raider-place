package social.raider.server.place.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    private String id;
    private String name;
    private String type;
    private Double latitude;
    private Double longitude;
    private String icon;
}


package social.raider.server.place.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import social.raider.server.place.persistence.model.PlaceHash;

public interface PlaceRepository extends CrudRepository<PlaceHash, String> {

}

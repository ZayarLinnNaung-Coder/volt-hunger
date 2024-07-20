package thesis.zayarlinnhtet.volt_hunger.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import thesis.zayarlinnhtet.volt_hunger.entity.Artists;

public interface ArtistRepo extends MongoRepository<Artists, String> {
}

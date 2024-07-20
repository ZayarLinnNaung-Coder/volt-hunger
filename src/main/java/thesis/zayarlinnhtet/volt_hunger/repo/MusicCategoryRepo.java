package thesis.zayarlinnhtet.volt_hunger.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import thesis.zayarlinnhtet.volt_hunger.entity.MusicCategory;

public interface MusicCategoryRepo extends MongoRepository<MusicCategory, String> {
}

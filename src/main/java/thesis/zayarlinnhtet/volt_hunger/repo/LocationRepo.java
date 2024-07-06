package thesis.zayarlinnhtet.volt_hunger.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import thesis.zayarlinnhtet.volt_hunger.entity.Location;

import java.util.List;

public interface LocationRepo extends MongoRepository<Location, String> {

  List<Location> findAllByOrderByNameAsc();

}

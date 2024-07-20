package thesis.zayarlinnhtet.volt_hunger.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import thesis.zayarlinnhtet.volt_hunger.entity.Music;

import java.util.List;

public interface MusicRepo extends MongoRepository<Music, String> {

    @Query("{ 'artists.id': ?0 }")
    List<Music> findByArtistId(String artistId);

}

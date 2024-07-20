package thesis.zayarlinnhtet.volt_hunger.controller;

import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import thesis.zayarlinnhtet.volt_hunger.domain.NewMusicRequest;
import thesis.zayarlinnhtet.volt_hunger.entity.Artists;
import thesis.zayarlinnhtet.volt_hunger.entity.Music;
import thesis.zayarlinnhtet.volt_hunger.entity.MusicCategory;
import thesis.zayarlinnhtet.volt_hunger.repo.ArtistRepo;
import thesis.zayarlinnhtet.volt_hunger.repo.MusicCategoryRepo;
import thesis.zayarlinnhtet.volt_hunger.repo.MusicRepo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/musics")
@CrossOrigin
public class MusicController {

    private final MusicRepo musicRepo;
    private final ArtistRepo artistRepo;
    private final MusicCategoryRepo musicCategoryRepo;

    @GetMapping
    List<Music> getAllMusic(@RequestParam(required = false) String artistId) {

        if(StringUtils.hasText(artistId)){
            return musicRepo.findByArtistId(artistId);
        }

        return musicRepo.findAll();
    }

    @PostMapping
    Music addMusic(@RequestBody NewMusicRequest music) {

        MusicCategory musicCategory = musicCategoryRepo.findById(music.getMusicCategoryId()).get();
        Artists artists = artistRepo.findById(music.getMusicArtistId()).get();

        Music musicEntity = new Music();
        musicEntity.setTitle(music.getTitle());
        musicEntity.setDescription(music.getDescription());
        musicEntity.setImageUrl(music.getImageUrl());
        musicEntity.setUrl(music.getUrl());
        musicEntity.setMusicCategory(musicCategory);
        musicEntity.setArtists(artists);

        return musicRepo.save(musicEntity);
    }

    @GetMapping("{id}")
    Music getMusicById(@PathVariable String id) {
        return musicRepo.findById(id).get();
    }

}
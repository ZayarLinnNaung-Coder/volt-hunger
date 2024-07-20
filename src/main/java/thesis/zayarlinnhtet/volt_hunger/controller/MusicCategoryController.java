package thesis.zayarlinnhtet.volt_hunger.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import thesis.zayarlinnhtet.volt_hunger.entity.MusicCategory;
import thesis.zayarlinnhtet.volt_hunger.repo.MusicCategoryRepo;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/music-categories")
public class MusicCategoryController {

    private final MusicCategoryRepo musicCategoryRepo;

    @PostMapping
    MusicCategory addMusicCategory(@RequestBody MusicCategory musicCategory) {
        return musicCategoryRepo.save(musicCategory);
    }

    @RequestMapping
    List<MusicCategory> getMusicCategories() {
        return musicCategoryRepo.findAll();
    }

}

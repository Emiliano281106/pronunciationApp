package dev.pronunciationAppBack.controller;

import dev.pronunciationAppBack.model.Level;
import dev.pronunciationAppBack.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/levels")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @GetMapping
    public ResponseEntity<List<Level>> getAllLevels(){

        List<Level> levels  = levelService.getAllLevels();

        if(levels.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(levels);
    }

    @GetMapping("/ {id}")
    public ResponseEntity<Level> getLevelById(@PathVariable String id){

        Optional<Level> level = levelService.getLevelById(id);

        return level.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PostMapping("/createLevel")
    public Level createLevel(@RequestBody Level level){

        return levelService.createLevel(level);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Level> updateLevel(@PathVariable String id, @RequestBody Level level){

        if(!levelService.levelExistsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }

        Level updateLevel = levelService.updateLevel(level);

        return ResponseEntity.ok(level);
    }

    @DeleteMapping
    public String deleteAllLevels(){

        levelService.deleteAllLevels();

        return "All levels deleted!";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLevelById(@PathVariable String idToDelete){

        if(!levelService.levelExistsById(idToDelete)){

            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        levelService.deleteLevelById(idToDelete);

        return  ResponseEntity.ok("Level deleted!");
    }







}

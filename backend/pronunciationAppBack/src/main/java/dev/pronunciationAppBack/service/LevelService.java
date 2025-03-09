package dev.pronunciationAppBack.service;

import dev.pronunciationAppBack.model.Level;
import dev.pronunciationAppBack.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    public List<Level> getAllLevels(){

        return levelRepository.findAll();
    }

    public Optional<Level> getLevelById(String id){

        return levelRepository.findById(id);
    }

    public Level createLevel(Level level){

        return levelRepository.save(level);
    }

    public Level updateLevel(Level level){

        return levelRepository.save(level);
    }

    public void deleteLevelById(String id){

        levelRepository.deleteById(id);


    }

    public void deleteAllLevels(){

        levelRepository.deleteAll();

    }

    public boolean levelExistsById(String id) {
        return levelRepository.existsById(id);
    }

}




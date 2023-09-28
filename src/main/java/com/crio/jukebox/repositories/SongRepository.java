package com.crio.jukebox.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.crio.jukebox.entities.Song;

public class SongRepository implements ISongRepository{

    private Map<String, Song> songRepoMap = new HashMap<>();
    private Integer autoIncrement = 0;

    public Song save(Song entity){
        if (entity.getId() == null){
            String id = String.valueOf(++autoIncrement);
            entity.setId(id);
            songRepoMap.put(id, entity);
            return entity;
        } else {
            songRepoMap.put(entity.getId(), entity); 
            return entity;
        }
    }
    public List<Song> findAll(){
        return songRepoMap.values().stream().collect(Collectors.toList());
    }
    public Optional<Song> findById(String songId){
        return Optional.ofNullable(songRepoMap.get(songId));
    }
    public boolean existsById(String songId){
        return songRepoMap.containsKey(songId);
    }
    public boolean delete(Song entity){
        return songRepoMap.remove(entity.getId(), entity);
    }
    public void deleteById(String songId){
        
    }
    public long count(){
        return 0;
    }


    public boolean existByIds(List<String> songIds){
        return songIds.stream().allMatch(s-> existsById(s));
    }

}
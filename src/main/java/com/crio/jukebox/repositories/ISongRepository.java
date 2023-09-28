package com.crio.jukebox.repositories;

import java.util.List;
import com.crio.jukebox.entities.Song;

public interface ISongRepository extends CRUDRepository<Song, String>{
    
    public boolean existByIds(List<String> songIds);
    
}
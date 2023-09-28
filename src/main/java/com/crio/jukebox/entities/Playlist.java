package com.crio.jukebox.entities;

import java.util.List;
import java.util.stream.Collectors;

public class Playlist extends BaseEntity{
    
    private List<String> songsIds;
    private static int idCounter = 0;

    public Playlist(String name, List<String> songsIds) {
        this();
        this.name = name;
        this.songsIds = songsIds;
    }

    public Playlist(){
        this.id = String.valueOf(++idCounter);
    }

    public int getSize(){
        return songsIds.size();
    }

    public List<String> getSongsIds() {
        return songsIds;
    }

    public void setSongsIds(List<String> songsIds) {
        this.songsIds = songsIds;
    }

    public Integer getIdCounter() {
        return idCounter;
    }

    @Override
    public String toString(){
        return "Playlist ID - " + this.id;
    }

    public String toStringInDetail(){
        String songIds = this.getSongsIds().stream().collect(Collectors.joining(" "));
        return this.toString() + "\nPlaylist Name - " + this.getName() + "\nSong IDs - " + songIds;
    }

    
}
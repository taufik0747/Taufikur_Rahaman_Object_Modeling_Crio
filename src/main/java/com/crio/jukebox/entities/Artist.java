package com.crio.jukebox.entities;

import java.util.List;


public class Artist extends BaseEntity{

    List<Song> album;
    
    public Artist(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    
}
package com.crio.jukebox.entities;

import java.util.List;
import java.util.stream.Collectors;

public class Song extends BaseEntity{

    String generName;
    String albumName;
    Artist originalArtist;
    List<Artist> featuredArtists;

    public Song(String name, String generName, String albumName, Artist originalArtist,
            List<Artist> featuredArtists) {
        this.name = name;
        this.generName = generName;
        this.albumName = albumName;
        this.originalArtist = originalArtist;
        this.featuredArtists = featuredArtists;
    }

    public String getGenerName() {
        return generName;
    }

    public void setGenerName(String generName) {
        this.generName = generName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Artist getOriginalArtist() {
        return originalArtist;
    }

    public void setOriginalArtist(Artist originalArtist) {
        this.originalArtist = originalArtist;
    }

    public List<Artist> getFeaturedArtists() {
        return featuredArtists;
    }

    public void setFeaturedArtists(List<Artist> featuredArtists) {
        this.featuredArtists = featuredArtists;
    }

   

    @Override
    public String toString() {
        String artists = featuredArtists.stream().map(Artist::getName).collect(Collectors.joining(","));
        return "Current Song Playing\n"
        + "Song - " + name
        + "\nAlbum - " + albumName
        + "\nArtists - " + artists;
    }


}
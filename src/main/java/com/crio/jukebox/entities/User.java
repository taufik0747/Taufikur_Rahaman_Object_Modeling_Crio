package com.crio.jukebox.entities;

import java.util.HashMap;
import java.util.Map;

public class User extends BaseEntity {

    private Map<String, Playlist> idPlaylistMap = new HashMap<>();

    private String livePlayListId = null;
    private String livePlaylistSongId = null;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return id + " " + this.name;
    }

    public void setASongLive(String livePlayListId, String liveSongInLivePlaylistId){
        this.livePlayListId = livePlayListId;
        this.setASongLive(liveSongInLivePlaylistId);
    }
    
    public void setASongLive(String liveSongInLivePlaylistId){
        this.livePlaylistSongId = liveSongInLivePlaylistId;
    }
    
    public Map<String, Playlist> getIdPlaylistMap() {
        return idPlaylistMap;
    }

    public String getLivePlayListId() {
        return livePlayListId;
    }

    public String getlivePlaylistSongId() {
        return livePlaylistSongId;
    }



}
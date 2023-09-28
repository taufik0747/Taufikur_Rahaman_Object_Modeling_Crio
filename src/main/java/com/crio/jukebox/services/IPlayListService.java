package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.entities.Playlist;

public interface IPlayListService {
    public Playlist createPlaylist(String userId, String playlistName, List<String> songIds) throws IllegalArgumentException;
    

    public boolean deletePlaylist(String userId, String playlistId);
    
    
    public Playlist addSongToPlaylist(String userId, String playlistId, List<String> songIds);
    

    public Playlist deleteSongFromPlaylist(String userId, String playlistId, List<String> songIds);
   

}
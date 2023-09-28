package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;

public interface IUserService {
    
    public User createUser(String name); 

    
    public Playlist createPlaylist(String userId, String playlistName, List<String> songIds);
    
    
    
    public boolean deletePlaylist(String userId, String playlistId); 

    
    public Playlist addSongToPlaylist(String userId, String playlistId, List<String> songIds);
    

    public Playlist deleteSongFromPlaylist(String userId, String playlistId, List<String> songIds);
    
    
    public Song playAPlaylist(String userId, String playlistId);
   

    public Song playBackSong(String userId);
    public Song playNextSong(String userId);
    public Song playSongById(String userId, String songId);
    

    
}
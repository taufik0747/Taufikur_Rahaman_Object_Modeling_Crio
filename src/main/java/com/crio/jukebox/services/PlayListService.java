package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.User;

public class PlayListService implements IPlayListService{

    IUserRepository userRepository;
    ISongRepository songRepository;

    public PlayListService(IUserRepository userRepository, ISongRepository songRepository){
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }
    
    public Playlist createPlaylist(String userId, String playlistName, List<String> songIds) throws IllegalArgumentException{
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("userId doesn't exists!"));
        Playlist playlistByName = user.getIdPlaylistMap().values().stream().filter(p->p.getName().equals(playlistName)).findFirst().orElse(null);
        if (playlistByName != null){
            throw new IllegalArgumentException("playlistName already exists!");
        }
        // Validate that each input songId exits in the Song repository
        if(!songRepository.existByIds(songIds)){
            throw new IllegalArgumentException("Some Requested Songs Not Available. Please try again.");
        }
        Playlist newPlaylist = new Playlist(playlistName, songIds);
        user.getIdPlaylistMap().put(newPlaylist.getId(), newPlaylist);
        return newPlaylist;
    }
    
    public boolean deletePlaylist(String userId, String playlistId){
        // DONE
        User user = userRepository.findById(userId).orElseThrow();
        Playlist playlist = user.getIdPlaylistMap().remove(playlistId);
        if (playlist == null) return false;
        return true;
    }

    public Playlist addSongToPlaylist(String userId, String playlistId, List<String> songIds){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("userId doesn't exists!"));
        Playlist playlist = user.getIdPlaylistMap().get(playlistId);
        if (playlist == null) throw new IllegalArgumentException("playlistId doesn't exists!");
        // Validate that each input songId exits in the Song repository
        if(!songRepository.existByIds(songIds)){
            throw new IllegalArgumentException("Some Requested Songs Not Available. Please try again.");
        }
        // Add the new songIds to pre-existing playlist and return the new playlist.
        playlist.getSongsIds().addAll(songIds);
        user.getIdPlaylistMap().put(playlistId, playlist);
        return user.getIdPlaylistMap().get(playlistId);
    }

    public Playlist deleteSongFromPlaylist(String userId, String playlistId, List<String> songIds){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("userId doesn't exists!"));
        Playlist playlist = user.getIdPlaylistMap().get(playlistId);
        if (playlist == null) throw new IllegalArgumentException("playlistId doesn't exists!");
        // Validate that each input songId exits in the Song repository
        if(!songRepository.existByIds(songIds)){
            throw new IllegalArgumentException("Some Requested Songs Not Available. Please try again.");
        }
        // Add the new songIds to pre-existing playlist and return the new playlist.
        playlist.getSongsIds().removeAll(songIds);
        user.getIdPlaylistMap().put(playlistId, playlist);
        return user.getIdPlaylistMap().get(playlistId);
    }
}
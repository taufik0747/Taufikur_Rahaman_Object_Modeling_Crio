package com.crio.jukebox.services;

import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;
import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;

public class UserService implements IUserService{

    ISongRepository songRepository;
    IUserRepository userRepository;
    IPlayListService playlistService;

    public UserService(ISongRepository songRepository, IUserRepository userRepository, IPlayListService playlistService){
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.playlistService = playlistService;
    }
        
    public User createUser(String name){
        User newUser = new User(name);
        User savedUser = userRepository.save(newUser);
        return savedUser;
    }

    // #3
    public Playlist createPlaylist(String userId, String playlistName, List<String> songIds)throws IllegalArgumentException{
        return playlistService.createPlaylist(userId, playlistName, songIds);
    }

    // #4
    public boolean deletePlaylist(String userId, String playlistId){
        return playlistService.deletePlaylist(userId, playlistId);
    }

    // #5
    public Playlist addSongToPlaylist(String userId, String playlistId, List<String> songIds) throws IllegalArgumentException{
        Playlist playlist = playlistService.addSongToPlaylist(userId, playlistId, songIds);
        return playlist;
    }

    public Playlist deleteSongFromPlaylist(String userId, String playlistId, List<String> songIds) throws IllegalArgumentException{
        Playlist playlist = playlistService.deleteSongFromPlaylist(userId, playlistId, songIds);
        return playlist;
    }

    // #6
    public Song playAPlaylist(String userId, String playlistId) throws IndexOutOfBoundsException, IllegalArgumentException{
        User user = this.getUserById(userId);
        Playlist playlist = getPlaylistById(user, playlistId);
        String liveSongId;
        try{
            liveSongId = playlist.getSongsIds().get(0);
            user.setASongLive(playlistId, liveSongId);
        } catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("Playlist is empty.");
        }
        return this.getSongById(liveSongId);
    }

    public Song playBackSong(String userId) throws IllegalArgumentException{
        User user = this.getUserById(userId);
        String activePlaylistId = getLivePlayListId(user);
        String activePlaylistSongId = getLivePlayListSongId(user);
        int activePlaylistSongIndex = getPlaylistSongIndex(user, activePlaylistId, activePlaylistSongId);
        int nextPlaylistSongIndex = getBackPlaylistSongIndex(user, activePlaylistId, activePlaylistSongIndex);
        String newLiveSongId = getSongIdFromSongIndexInPlaylist(user, activePlaylistId, nextPlaylistSongIndex);
        user.setASongLive(newLiveSongId);
        return this.getSongById(newLiveSongId);
    }

    public Song playNextSong(String userId) throws IllegalArgumentException{
        User user = this.getUserById(userId);
        String activePlaylistId = getLivePlayListId(user);
        String activePlaylistSongId = getLivePlayListSongId(user);
        int activePlaylistSongIndex = getPlaylistSongIndex(user, activePlaylistId, activePlaylistSongId);
        int nextPlaylistSongIndex = getNextPlaylistSongIndex(user, activePlaylistId, activePlaylistSongIndex);
        String newLiveSongId = getSongIdFromSongIndexInPlaylist(user, activePlaylistId, nextPlaylistSongIndex);
        user.setASongLive(newLiveSongId);
        return this.getSongById(newLiveSongId);
    }

    public Song playSongById(String userId, String songId) throws IllegalArgumentException{
        User user = this.getUserById(userId);
        String activePlaylistId = getLivePlayListId(user);
        Song song = getSongByIdFromPlaylist(user, activePlaylistId, songId);
        user.setASongLive(songId);
        return song;
    }


    // ************ secondary methods ************
    public User getUserById(String userId) throws IllegalArgumentException{
        return userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("userId doesn't exists!"));
    }

    public Playlist getPlaylistById(User user, String playlistId) throws IllegalArgumentException{
        Playlist playlist = user.getIdPlaylistMap().get(playlistId);
        if (playlist == null) throw new IllegalArgumentException("playlistId doesn't exists!");
        return playlist;
    }

    public String getLivePlayListId(User user) throws IllegalArgumentException{
        String livePlaylistId = user.getLivePlayListId();
        if (livePlaylistId == null) throw new IllegalArgumentException("User doesn't have any active playlist!");
        return livePlaylistId;
    }

    public String getLivePlayListSongId(User user) throws IllegalArgumentException{
        String livePlaylistSongId = user.getlivePlaylistSongId();
        if (livePlaylistSongId == null) throw new IllegalArgumentException("User doesn't have any active song!");
        return livePlaylistSongId;
    }

    public Song getSongById(String songId) throws IllegalArgumentException{
        return songRepository.findById(songId).orElseThrow(()-> new IllegalArgumentException("songId doesn't exists in Repo!"));
    }

    public Song getSongByIdFromPlaylist(User user, String playlistId, String songId){
        if (! getPlaylistById(user, playlistId).getSongsIds().contains(songId)){
            throw new IllegalArgumentException("Given song id is not a part of the active playlist");
        }
        return getSongById(songId);
    }

    public int getPlaylistSongIndex(User user, String playlistId, String playlistSongId){
        int index = user.getIdPlaylistMap().get(playlistId).getSongsIds().indexOf(playlistSongId);
        if (index == -1) throw new IllegalArgumentException("Given song id is not a part of the active playlist");
        return index;
    }

    public int getBackPlaylistSongIndex(User user, String activePlaylistId, int activePlaylistSongIndex){
        int sizeOfPlaylist = this.getPlaylistById(user, activePlaylistId).getSize();
        if (activePlaylistSongIndex == 0) return --sizeOfPlaylist;
        return --activePlaylistSongIndex;
    }

    public int getNextPlaylistSongIndex(User user, String activePlaylistId, int activePlaylistSongIndex){
        int sizeOfPlaylist = this.getPlaylistById(user, activePlaylistId).getSize();
        if (activePlaylistSongIndex == --sizeOfPlaylist) return 0;
        return ++activePlaylistSongIndex;
    }

    public String getSongIdFromSongIndexInPlaylist(User user, String activePlaylistId, int activePlaylistSongIndex){
        String liveSongId = user.getIdPlaylistMap().get(activePlaylistId).getSongsIds().get(activePlaylistSongIndex);
        return liveSongId;
    }


}
package com.crio.jukebox.commands;

import java.util.List;
import java.util.stream.Collectors;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.services.IUserService;

public class ModifyPlayListCommand implements ICommand{
    IUserService userService;

    public ModifyPlayListCommand (IUserService userService){
        this.userService = userService;
    }
    
    public void execute(List<String> tokens) {

       

        String userId = tokens.get(2);
        String playlistId = tokens.get(3);
        List<String> songIds = tokens.stream().skip(4).collect(Collectors.toList());

        try {
           
            if (tokens.get(1).equals("ADD-SONG")) {
                Playlist playlist = userService.addSongToPlaylist(userId, playlistId, songIds);
                System.out.println(playlist.toStringInDetail());
            } else if (tokens.get(1).equals("DELETE-SONG")) {
                Playlist playlist = userService.deleteSongFromPlaylist(userId, playlistId, songIds);
                System.out.println(playlist.toStringInDetail());
            } else
                throw new IllegalArgumentException("Wrong token passed with MODIFY-PLAYLIST!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
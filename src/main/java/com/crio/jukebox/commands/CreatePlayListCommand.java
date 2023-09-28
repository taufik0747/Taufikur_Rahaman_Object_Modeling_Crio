package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.services.IUserService;
import com.crio.jukebox.entities.Playlist;

public class CreatePlayListCommand implements ICommand{
    
    IUserService userService;

    public CreatePlayListCommand(IUserService userService) {
        this.userService = userService;
    }

    public void execute(List<String> tokens){
        
        List<String> songIds = new ArrayList<>();
        for (int i = 3; i < tokens.size(); i++){
            songIds.add(tokens.get(i));
        }

        Playlist newPlaylist = null;
        try{
            newPlaylist = userService.createPlaylist(tokens.get(1), tokens.get(2), songIds);
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println(newPlaylist.toString());
    }
    
}
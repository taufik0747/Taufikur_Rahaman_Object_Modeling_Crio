package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.services.IUserService;

public class PlayPlayListCommand implements ICommand{

    IUserService userService;

    public PlayPlayListCommand (IUserService userService){
        this.userService = userService;
    }
    
    public void execute(List<String> tokens){
       
        if (tokens.size() < 3 || tokens.size() > 3) throw new IllegalArgumentException("ddd");
        try{
            Song song = userService.playAPlaylist(tokens.get(1), tokens.get(2));
            System.out.println(song);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }


    }

    
    
}
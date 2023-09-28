package com.crio.jukebox.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.crio.jukebox.services.ISongService;

public class LoadDataCommand implements ICommand{

    ISongService songService;
    
    public LoadDataCommand(ISongService songService) {
        this.songService = songService;
    }

    public void execute(List<String> tokens){
        
        String output = null;
        if (tokens.size() < 2 || tokens.size() > 2){
            System.out.println("Invalid command encountered! Only 2 tokens expected!");
        } else {
            String csvFileName = tokens.get(1);
            try{
                output = songService.loadSongsFromFile(new File(csvFileName));
            } catch (IOException e){
                output = e.getMessage();
            }
            System.out.println(output);
        }
    }
    
}
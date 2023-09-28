package com.crio.jukebox.appConfig;

import com.crio.jukebox.commands.CreateUserCommand;
import com.crio.jukebox.commands.DeletePlayListCommand;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.repositories.UserRepository;
import com.crio.jukebox.services.UserService;
import com.crio.jukebox.commands.CommandInvoker;
import com.crio.jukebox.commands.CreatePlayListCommand;


import com.crio.jukebox.commands.LoadDataCommand;
import com.crio.jukebox.commands.ModifyPlayListCommand;

import com.crio.jukebox.commands.PlayPlayListCommand;

import com.crio.jukebox.commands.PlaySongCommand;
import com.crio.jukebox.repositories.ArtistRepository;
import com.crio.jukebox.repositories.IArtistRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.SongRepository;
import com.crio.jukebox.services.IPlayListService;

import com.crio.jukebox.services.ISongService;
import com.crio.jukebox.services.IUserService;
import com.crio.jukebox.services.PlayListService;

import com.crio.jukebox.services.SongService;

public class ApplicationConfig {

    // Repositories
    ISongRepository songRepository = new SongRepository();
    IUserRepository userRepository = new UserRepository();
    IArtistRepository artistRepository = new ArtistRepository();
    
    // Services
    ISongService songService = new SongService(songRepository);
    IPlayListService playlistService = new PlayListService(userRepository, songRepository);
    IUserService userService = new UserService(songRepository, userRepository, playlistService);

    // Commands
    LoadDataCommand loadDataCommand = new LoadDataCommand(songService);
    CreateUserCommand createUserCommand = new CreateUserCommand(userService);
    CreatePlayListCommand CreatePlaylistCommand = new CreatePlayListCommand(userService);
    DeletePlayListCommand deletePlaylistCommand = new DeletePlayListCommand(userService);
    PlayPlayListCommand playPlaylistCommand = new PlayPlayListCommand(userService);
    ModifyPlayListCommand modifyPlaylistCommand = new ModifyPlayListCommand(userService);
    PlaySongCommand playSongCommand = new PlaySongCommand(userService);


    CommandInvoker commandInvoker = new CommandInvoker();
 

    
        public CommandInvoker getCommandInvoker(){
            commandInvoker.register("CREATE-USER", createUserCommand);
            commandInvoker.register("CREATE-PLAYLIST", CreatePlaylistCommand);
            commandInvoker.register("DELETE-PLAYLIST", deletePlaylistCommand);
            commandInvoker.register("LOAD-DATA", loadDataCommand);
            commandInvoker.register("PLAY-PLAYLIST", playPlaylistCommand);
            commandInvoker.register("MODIFY-PLAYLIST", modifyPlaylistCommand);            
            commandInvoker.register("PLAY-SONG", playSongCommand);

        return commandInvoker;
    }


}
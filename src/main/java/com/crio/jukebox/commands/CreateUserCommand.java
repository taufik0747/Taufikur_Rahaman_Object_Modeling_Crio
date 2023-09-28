package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.services.IUserService;

public class CreateUserCommand implements ICommand{

    IUserService userService;

    public CreateUserCommand(IUserService userService) {
        this.userService = userService;
    }

    
    public void execute(List<String> tokens){

        if (tokens.size() !=2 || tokens.get(1) == null) throw new IllegalArgumentException();
        User savedUser = userService.createUser(tokens.get(1));
        System.out.println(savedUser);
    }
}
package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.services.IUserService;

public class LeaderBoardCommand implements ICommand{

    private final IUserService userService;
    
    public LeaderBoardCommand(IUserService userService) {
        this.userService = userService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute getAllUserScoreOrderWise method of IUserService and print the result.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["LEADERBOARD","ASC"]
    // or
    // ["LEADERBOARD","DESC"]

    @Override
    public void execute(List<String> tokens) {
        try {
            // Get the score order from the input tokens
            ScoreOrder scoreOrder = ScoreOrder.valueOf(tokens.get(1));
            
            // Get the list of users based on the score order
            List<User> userList = userService.getAllUserScoreOrderWise(scoreOrder);
            
            StringBuilder output = new StringBuilder("[");
            for (User user : userList) {
                output.append(user).append(", ");
            }
            if (!userList.isEmpty()) {
                
                output.delete(output.length() - 2, output.length()).append("]");
            } else {
                output.append("]");
            }
            System.out.println(output.toString());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid score order. Please provide 'ASC' or 'DESC'.");
        }
        
    }
    
}

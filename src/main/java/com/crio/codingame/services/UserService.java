package com.crio.codingame.services;

//import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.RegisterationStatus;
import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.IContestRepository;
import com.crio.codingame.repositories.IUserRepository;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IContestRepository contestRepository;
    private Integer autoIncrement=0;

    public UserService(IUserRepository userRepository, IContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.contestRepository = contestRepository;
        // this.autoIncrement = userRepository.findAll().size();
    }
    // TODO: CRIO_TASK_MODULE_SERVICES
    // Create and store User into the repository.
    @Override
    public User create(String name) {
        String userId=Integer.toString(++autoIncrement);
        User newUser=new User(userId, name, 1500);
        userRepository.save(newUser);
        return newUser;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Get All Users in Ascending Order w.r.t scores if ScoreOrder ASC.
    // Or
    // Get All Users in Descending Order w.r.t scores if ScoreOrder DESC.

    @Override
public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder){
    List<User> userList = userRepository.findAll();

    Comparator<User> comparator = (u1, u2) -> {
        if (scoreOrder == ScoreOrder.ASC) {
            return Integer.compare(u1.getScore(), u2.getScore());
        } else if (scoreOrder == ScoreOrder.DESC) {
            return Integer.compare(u2.getScore(), u1.getScore());
        } else {
            return 0; // Default case when scoreOrder is neither ASC nor DESC.
        }
    };

    return userList.stream()
            .sorted(comparator)
            .collect(Collectors.toList());     
            
}

    @Override
    public UserRegistrationDto attendContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException("Cannot Attend Contest. Contest for given id:"+contestId+" not found!"));
        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException("Cannot Attend Contest. User for given name:"+ userName+" not found!"));
        if(contest.getContestStatus().equals(ContestStatus.IN_PROGRESS)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is in progress!");
        }
        if(contest.getContestStatus().equals(ContestStatus.ENDED)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is ended!");
        }
        if(user.checkIfContestExists(contest)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is already registered!");
        }
        user.addContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(),RegisterationStatus.REGISTERED);
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Withdraw the user from the contest
    // Hint :- Refer Unit Testcases withdrawContest method

    @Override
    public UserRegistrationDto withdrawContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
     // Retrieve the contest and user objects from the repositories.
    Contest contest = contestRepository.findById(contestId)
    .orElseThrow(() -> new ContestNotFoundException("Cannot withdraw from the contest. Contest not found."));

User user = userRepository.findByName(userName)
    .orElseThrow(() -> new UserNotFoundException("Cannot withdraw from the contest. User not found."));

// Check if the user is the creator of the contest.
if (user.equals(contest.getCreator())) {
throw new InvalidOperationException("The contest creator cannot withdraw from the contest.");
}

// Check if the contest is in progress or has ended.
if (contest.getContestStatus() == ContestStatus.IN_PROGRESS) {
throw new InvalidOperationException("Cannot withdraw from the contest. The contest is in progress.");
} else if (contest.getContestStatus() == ContestStatus.ENDED) {
throw new InvalidOperationException("Cannot withdraw from the contest. The contest has ended.");
}

// Check if the user is registered for the contest.
if (!user.checkIfContestExists(contest)) {
throw new InvalidOperationException("Cannot withdraw from the contest. User is not registered for the contest.");
}

// Remove the user from the contest.
user.deleteContest(contest);
userRepository.save(user);

return new UserRegistrationDto(contest.getName(), user.getName(), RegisterationStatus.NOT_REGISTERED);
    }
    
}

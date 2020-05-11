package br.com.leaderboard.services.impl;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import br.com.leaderboard.dto.Score;
import br.com.leaderboard.dto.User;
import br.com.leaderboard.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
	
	@Override
	public User addOrUpdateUser(Score score) {
		
		int index = Collections.binarySearch(users, score.getUserId());
		
		User userReturn;
		
		if(index<0) {
			userReturn = new User(score.getUserId(), score.getScore());
			users.add((index*-1)-1, userReturn);
			return new User(userReturn);
		}
		else {			
			userReturn = users.get(index);
			userReturn.setLastScore(userReturn.getScore());
			userReturn.addScore(score.getScore());
			
			return new User(userReturn);
		}
	}

	@Override
	public Optional<User> getUser(int userId) {
		int index = Collections.binarySearch(users, userId);
		if(index>=0) {
			return Optional.of(new User(users.get(index)));
		}
		else {
			return Optional.empty();
		}
		
	}

}

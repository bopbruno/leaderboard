package br.com.leaderboard.services;

import java.util.Optional;

import br.com.leaderboard.dto.Score;
import br.com.leaderboard.dto.User;


public interface UserService {
	
	User addOrUpdateUser(Score score);
	
	Optional<User>  getUser(int userId);
	
}
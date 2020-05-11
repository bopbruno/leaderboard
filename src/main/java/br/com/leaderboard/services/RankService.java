package br.com.leaderboard.services;

import java.util.List;

import br.com.leaderboard.dto.User;
import br.com.leaderboard.dto.UserDto;

public interface RankService {
	
	int getUserPosition(User user);
	
	List<UserDto> getHighScoreList(int limit);

	void addOrUpdateUserPosition(User user);
	
}
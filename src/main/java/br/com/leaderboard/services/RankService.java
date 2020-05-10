package br.com.leaderboard.services;

import java.util.List;

import br.com.leaderboard.dto.ScoreLine;
import br.com.leaderboard.dto.User;

public interface RankService {
	
	int getUserPosition(User user);
	
	List<ScoreLine> getHighScoreList(int limit);

	void addOrUpdateUserPosition(User user);
	
}
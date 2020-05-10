package br.com.leaderboard.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.leaderboard.dto.ScoreLine;
import br.com.leaderboard.dto.User;
import br.com.leaderboard.services.RankService;

@Service
public class RankServiceImpl implements RankService {

	ArrayList<ScoreLine> leaderboard = new ArrayList<>();

	public RankServiceImpl() {

	}

	@Override
	public void addOrUpdateUserPosition(User user) {

		if (user.getLastScore() == -1) {

			int indexScore = Collections.binarySearch(leaderboard, user.getScore());

			if (indexScore >= 0) {
				ScoreLine scoreLine = leaderboard.get(indexScore);
				scoreLine.getUsers().add(user);

			} else {
				leaderboard.add((indexScore * -1) - 1, new ScoreLine(user));
			}

		} else {
			int indexScore = Collections.binarySearch(leaderboard, user.getLastScore());

			ScoreLine scoreLine = leaderboard.get(indexScore);

			scoreLine.getUsers().remove(user);

			if (scoreLine.getUsers().isEmpty()) {
				leaderboard.remove(indexScore);
			}

			int indexNewScore = Collections.binarySearch(leaderboard, user.getScore());

			if (indexNewScore >= 0) {
				ScoreLine scoreLine2 = leaderboard.get(indexNewScore);
				scoreLine2.getUsers().add(user);

			} else {
				leaderboard.add((indexNewScore * -1) - 1, new ScoreLine(user));
			}
		}

	}

	@Override
	public List<ScoreLine> getHighScoreList(int limit) {
		
		if(leaderboard.isEmpty()) {
			return new ArrayList<ScoreLine>();
		}
		
		int listLimit = limit >= leaderboard.size() ? leaderboard.size(): limit;
		
		return leaderboard.subList(0, listLimit);
	}

	@Override
	public int getUserPosition(User user) {
		int indexScore = Collections.binarySearch(leaderboard, user.getScore());
		return indexScore+1;
	}

}

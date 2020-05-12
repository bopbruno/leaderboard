package br.com.leaderboard.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.leaderboard.dto.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RankServiceTest {

	@Autowired
	private RankService rankService;
	
	@Test
	public void testOrdenacaoPosicaoUsuario() {
		
		User user1 = new User(1, 100);
		User user2 = new User(2, 200);
		User user3 = new User(3, 300);
		
		rankService.addOrUpdateUserPosition(user1);
		rankService.addOrUpdateUserPosition(user2);
		rankService.addOrUpdateUserPosition(user3);
		
		assert(rankService.getUserPosition(user1) == 3 &&
				rankService.getUserPosition(user2) == 2 &&
				rankService.getUserPosition(user3) == 1
				);
	}
	
	@Test
	public void testLeaderboardNaoVazia() {
		User user1 = new User(4, 1);
		rankService.addOrUpdateUserPosition(user1);
		assert(!rankService.getHighScoreList(1).isEmpty());
	}
	
	
}

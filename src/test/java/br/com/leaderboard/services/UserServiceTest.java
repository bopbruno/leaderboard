package br.com.leaderboard.services;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.leaderboard.dto.Score;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	
	@Test
	public void adicionarNovoUsuario() {

		Score score = new Score();
		score.setScore(10);
		score.setUserId(3);
		userService.addOrUpdateUser(score);
		
		assertTrue(userService.getUser(3).get().getId() == 3);
	
	}
	
	@Test
	public void adicionarUsuarioJaExistente() {
		Score score = new Score();
		score.setScore(10);
		score.setUserId(2);
		
		userService.addOrUpdateUser(score);
		userService.addOrUpdateUser(score);
		
		assertTrue(userService.getUser(2).get().getScore() == 20);
	}
	
	@Test
	public void atualizarLastScoreUsuarioExistente() {
		Score score = new Score();
		score.setScore(10);
		score.setUserId(1);
		
		userService.addOrUpdateUser(score);
		userService.addOrUpdateUser(score);
		
		assertTrue(userService.getUser(1).get().getLastScore() == 10);
	}
}

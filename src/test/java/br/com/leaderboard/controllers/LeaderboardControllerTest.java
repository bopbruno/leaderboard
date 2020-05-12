package br.com.leaderboard.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.leaderboard.dto.User;
import br.com.leaderboard.dto.UserDto;
import br.com.leaderboard.services.RankService;
import br.com.leaderboard.services.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LeaderboardControllerTest {

	@MockBean
	private RankService rankService;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void testPegarPosicaoUsuario() throws Exception {
		
		BDDMockito.given(this.userService.getUser(Mockito.anyInt())).willReturn(Optional.of(new User(1, 10)));
		BDDMockito.given(this.rankService.getUserPosition(Mockito.anyObject())).willReturn(2);
		
		mvc.perform(MockMvcRequestBuilders.get("/1/position").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.position").value(2))
			.andExpect(jsonPath("$.score").value(10))
			.andExpect(jsonPath("$.userId").value(1));
		
	}
	
	@Test
	public void testPegarHighScoreList() throws Exception {
		
		UserDto userDto = new UserDto();
		userDto.setPosition(1);
		userDto.setScore(10);
		userDto.setUserId(2);
		
		ArrayList<UserDto> highScoreList = new ArrayList<UserDto>();
		
		highScoreList.add(userDto);
		
		BDDMockito.given(this.rankService.getHighScoreList(Mockito.anyInt())).willReturn(highScoreList);
		
		mvc.perform(MockMvcRequestBuilders.get("/highscorelist").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1));
		
	}
	
}

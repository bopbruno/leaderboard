package br.com.leaderboard.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.leaderboard.dto.Score;
import br.com.leaderboard.dto.ScoreLine;
import br.com.leaderboard.dto.User;
import br.com.leaderboard.dto.UserDto;
import br.com.leaderboard.services.RankService;
import br.com.leaderboard.services.UserService;

@RestController
public class LeaderboardController {

	@Autowired
	private RankService rankService;
	@Autowired
	private UserService userService;

	public LeaderboardController() {
	}

	@PostMapping(value = "/score")
	public void addOrUpdateUser(@Valid @RequestBody Score score) {
		synchronized (score) {
			User user = userService.addOrUpdateUser(score);
			rankService.addOrUpdateUserPosition(user);
		}		
	}

	@GetMapping(value = "/{userId}/position")
	public UserDto getUser(@PathVariable("userId") int userId) {
		
		Optional<User> user= userService.getUser(userId);
		
		if(!user.isPresent()) {
			return new UserDto();
		}
		
		UserDto userDto = this.convertUserTouserDto(user.get());
		
		userDto.setPosition(rankService.getUserPosition(user.get()));
		
		return userDto;
	}

	@GetMapping(value = "/highscorelist")
	public List<UserDto> getHighScoreList(){		
		
		int limit = 2000;
		
		List<ScoreLine> scores = rankService.getHighScoreList(limit);
		
		List<UserDto> highScoreList = new ArrayList<>();
		
		int position = 1;
		for(ScoreLine sl : scores) {
			
			List<User> users = sl.getUsers();
			
			for(User user : users) {
				UserDto userDto = this.convertUserTouserDto(user);
				userDto.setPosition(position);
				highScoreList.add(userDto);
				
				if(position > limit) {
					return highScoreList;
				}
				
			}
			
			position++;
		}
		
		return highScoreList;
	}
	
	private UserDto convertUserTouserDto(User user) {
		UserDto userDto = new UserDto();		
		userDto.setUserId(user.getId());
		userDto.setScore(user.getScore());
		
		return userDto;
	}
}

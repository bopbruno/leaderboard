package br.com.leaderboard.controllers;

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
import br.com.leaderboard.dto.User;
import br.com.leaderboard.dto.UserDto;
import br.com.leaderboard.services.RankService;
import br.com.leaderboard.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Leaderboard")
public class LeaderboardController {

	@Autowired
	private RankService rankService;
	@Autowired
	private UserService userService;

	public LeaderboardController() {
	}

	@PostMapping(value = "/score")
	@ApiOperation(value = "add a user and score or if the user exist, it sums the user's score (score = current score + new points)")
	public void addOrUpdateUser(@Valid @RequestBody Score score) {
			User user = userService.addOrUpdateUser(score);
			rankService.addOrUpdateUserPosition(user);
	}

	@GetMapping(value = "/{userId}/position")
	@ApiOperation(value = "Retrieves the current position of a specific user, considering the score for all users.")
	public UserDto getUser(@PathVariable("userId") int userId) {
		
		Optional<User> user= userService.getUser(userId);
		
		if(!user.isPresent()) {
			return null;
		}
		
		UserDto userDto = new UserDto(user.get(),rankService.getUserPosition(user.get()));
		
		return userDto;
	}

	@GetMapping(value = "/highscorelist")
	@ApiOperation(value = "Retrieves the high scores list, in descending order by score, limited to the 20000 higher scores.")
	public List<UserDto> getHighScoreList(){		
		
		int limit = 2;
		
		return rankService.getHighScoreList(limit);		
		
	}

}

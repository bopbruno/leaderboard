package br.com.leaderboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {


	private int userId;
	private int score;	
	private int position;
	
	public UserDto(User user, int position) {
		this.userId = user.getId();
		this.score = user.getScore();
		this.position = position;
	}	
}

package br.com.leaderboard.dto;

import lombok.Data;

@Data
public class UserDto {

	private int userId;
	private int score;	
	private int position;
}

package br.com.leaderboard.dto;

import java.util.ArrayList;

import lombok.Data;

/**
 * 
 * @author Bruno de Oliveira Pedrosa
 * Foi implementada a interface Comparable para
 * possibilitar o uso de busca binaria
 *
 */

@Data
public class ScoreLine implements Comparable<Integer>{

	private Integer score;
	private ArrayList<User> users;
	
	public ScoreLine(User user) {
		this.score = user.getScore();
		this.users = new ArrayList<User>();
		this.users.add(user);
	}
	
	@Override
	public int compareTo(Integer o) {
		return o.compareTo(this.getScore());
	}
	
}

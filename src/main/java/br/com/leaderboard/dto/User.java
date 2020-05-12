package br.com.leaderboard.dto;


import lombok.Data;

/**
 * 
 * @author Bruno de Oliveira Pedrosa
 * Foi implementada a interface Comparable para
 * possibilitar o uso de busca binaria
 *
 */

@Data
public class User implements Comparable<Integer>{

	private Integer Id;
	private int score;
	private int lastScore;
	
	public User(int id, int score) {
		this.Id = id;
		this.score = score;
		this.lastScore =  -1;
	}
		
	public User(User userReturn) {
		this.Id = userReturn.getId();
		this.score = userReturn.getScore();
		this.lastScore =  userReturn.getLastScore();
	}

	public void addScore(int score) {
		this.setScore(this.getScore() + score);
	}
	
	@Override
	public int compareTo(Integer o) {
		return this.getId().compareTo(o);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		return result;
	}
	
	
	
}

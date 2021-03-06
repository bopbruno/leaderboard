package br.com.leaderboard.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.leaderboard.dto.ScoreLine;
import br.com.leaderboard.dto.User;
import br.com.leaderboard.dto.UserDto;
import br.com.leaderboard.services.RankService;

/**
 * 
 * @author Bruno de Oliveira Pedrosa
 * Para essa classe foi escolhido uma  ArrayList e sincronização com os metodos que usam a lista,
 * para evitar inconcistencia no retorno da lista com maiores score, caso por exemplo um usuário
 * esteja sendo removido e adicionado em outra posição e uma solicitação de lista de maiores
 * score é feita, resultando em uma lista onde o usuário em questão não apareça.
 * Para otimizar o tempo de busca as posições são inseridos pela ordem score que no caso
 * são numeros inteiros, assim é possivel usar a busca binaria, que é a forma mais 
 * rápida de buscar em uma lista. A posição do usuário é calculada no momento que a 
 * solicitação é realizada e é retornada de acorod com o incice da lista. Para evitar problemas de 
 * concorrencia quando a lista está sendo modificada, todas as referencias de objetos da classe 
 * não são enviadas para outras classe. São enviados novos objetos.
 *
 */

@Service
public class RankServiceImpl implements RankService {

	private static ArrayList<ScoreLine> leaderboard = new ArrayList<>();

	public RankServiceImpl() {

	}

	@Override
	public void addOrUpdateUserPosition(User user) {
		synchronized (leaderboard) {
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

			try { 
		         //Thread.sleep(10000); 
		     } 
		     catch (Exception e) { 
		         System.out.println("Child Thread"
		                  + " going to add element"); 
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

	}

	@Override
	public List<UserDto> getHighScoreList(int limit) {
		synchronized (leaderboard) {
			
			List<UserDto> userDtos = new ArrayList<UserDto>();
			
			if(leaderboard.isEmpty()) {
				return userDtos;
			}
			
			int listLimit = limit > leaderboard.size() ? leaderboard.size(): limit;
			
			int position = 1;
			
			List<ScoreLine> highScoreList = leaderboard.subList(0, listLimit);
			
			for(ScoreLine sl : highScoreList) {
				
				List<User> users = sl.getUsers();
				
				for(User user : users) {
					UserDto userDto = new UserDto(user,position);
					userDtos.add(userDto);					
				}
				
				position++;
			}
			
			return userDtos;
			
		}
		
	}

	@Override
	public int getUserPosition(User user) {
		synchronized (leaderboard) {
		int indexScore = Collections.binarySearch(leaderboard, user.getScore());
		return indexScore+1;
		}
	}

}

package br.com.leaderboard.services.impl;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import br.com.leaderboard.dto.Score;
import br.com.leaderboard.dto.User;
import br.com.leaderboard.services.UserService;


/**
 * 
 * @author Bruno de Oliveira Pedrosa
 *
 *	A lista CopyOnWriteArrayList foi escolhida pois ela evita problemas de concorrencia
 *	para otimizar o tempo de busca os usuário são inseridos pela ordem de id que no caso
 *	são numeros inteiros, assim é possivel usar a busca binaria, que é a forma mais 
 *	rápida de buscar em uma lista. Essa lista é mais indicada para ocasiões que não precisa
 *	de uma grande quantidade de remoções e a inserções na mesma. Como a maioria das
 *	operações será de consulta isso não isso não será um problema. Para evitar problemas de 
 * 	concorrencia quando a lista está sendo modificada, todas as referencias de objetos dessa classe 
 * 	não são enviadas para outras classe. São enviados novos objetos.
 *
 */

@Service
public class UserServiceImpl implements UserService {

	private CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
	
	@Override
	public User addOrUpdateUser(Score score) {
		
		int index = Collections.binarySearch(users, score.getUserId());
		
		User userReturn;
		
		if(index<0) {
			userReturn = new User(score.getUserId(), score.getScore());
			users.add((index*-1)-1, userReturn);
			return new User(userReturn);
		}
		else {			
			userReturn = users.get(index);
			userReturn.setLastScore(userReturn.getScore());
			userReturn.addScore(score.getScore());
			
			return new User(userReturn);
		}
	}

	@Override
	public Optional<User> getUser(int userId) {
		int index = Collections.binarySearch(users, userId);
		if(index>=0) {
			return Optional.of(new User(users.get(index)));
		}
		else {
			return Optional.empty();
		}
		
	}

}

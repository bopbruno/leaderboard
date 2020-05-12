[![Build Status](https://travis-ci.org/bopbruno/leaderboard.svg?branch=master)](https://travis-ci.org/bopbruno/leaderboard)

# Leaderboard API
API feita com Java e Spring Boot.

# Segue dois jeitos de usar a API:

# 1° opção:

# Endpoint:
Para usar a API basta usar a seguinte URL:

https://immense-temple-69759.herokuapp.com/swagger-ui.html


# 2° opção:
# Como executar localmente

Pré requisitos obrigatórios:
	-Porta local 8080 não pode estar sendo usada por outro programa
	-git e java 8 devem estar instalados na máquina local
	-executar os comandos no windows 10
	-certifique-se de não ter bloqueio de firewall, proxy, etc que impeça o acesso a URL https://github.com/bopbruno/leaderboard.git via prompt de comando
	
Execute os comandos:

1° - execute os passo pelo prompt de comando
2° - acesse a pasta que você deseja baixar o projeto
3° - execute: git clone https://github.com/bopbruno/leaderboard.git
4° - execute: cd leaderboard
5° - execute: mvnw.cmd clean install
6° - execute: mvnw.cmd spring-boot:run
7° - acesse a URL http://localhost:8080/swagger-ui.html
# Reversi Bot
General tool for playing Reversi with players or bots and testing impact of different game aspects. 

Description
-----------
Project allows playing Reversi against bot, playing it with 2 players or looking how 2 different bots
are trying to win the game. Each bot has implemented Minimax algorithm with appropriate heuristic function.
It is possible to define depth of each bot to decrease or increase level of gameplay, but it also takes more
time to make each move. 

There is also possibility to run genetic algorithm with defined by user parameters which shows coefficients
of each heuristic function to see their impacts on game. When it ends, there is also information about main
game statistics like average game length or average amount of available moves. 


Requirements
------------
* [Java Development Kit][Java] 17.0.1
* [Maven][Maven] 3.9.6

[Java]: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
[Maven]: https://maven.apache.org/download.cgi

Usage
-----
After downloading JDK and Maven we are ready to compile project using command: 
```sh
mvn clean install
```
This command will compile the code, remove old installation (if exists) and package the application into a JAR file located in the `target/` directory.

To run application use command:
```sh
java -jar target/Reversi-1.0.jar help
```
It should display list of all available commands which are also described below.

Commands
--------

### Player vs Player
To run game with 2 players run command:
```sh
java -jar target/Reversi-1.0.jar pvp
```
It will start game with 2 players making them moves by inputting field coordinates (two-digit number, 
1st digit means number of row, 2nd means number of column).

### Player vs Computer
To run game with player against bot run command:
```sh
java -jar target/Reversi-1.0.jar pvc <DEPTH>
```
Similarly, it will launch game with 1 player inputting his moves and bot who will run Minimax algorithm
with random coefficients on given depth.

### Computer vs Computer
To run game with 2 bots run command:
```sh
java -jar target/Reversi-1.0.jar cvc <DEPTH1> <DEPTH2>
```
This time, program will show board states after each move of bot. Their depth can be set by declaring
arguments `DEPTH1` and `DEPTH2`.

### Genetic Algorithm
Launching genetic algorithm can be done with command:
```sh
java -jar target/Reversi-1.0.jar genetic <DEPTH> <THREADS> <LOOPS>
```
It will start running loops of genetic algorithm. After each loop, the best individuals (heuristic 
functions) will be printed to output with their coefficients. This process can take some time but 
decreasing depth or loops can speed it up. Running it on higher amount of threads will also lead to
smaller working time because games will be run asynchronously.
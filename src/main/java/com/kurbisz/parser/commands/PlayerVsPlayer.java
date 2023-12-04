package com.kurbisz.parser.commands;

import com.kurbisz.GameServer;
import com.kurbisz.player.HumanPlayer;

/**
 * Command responsible for starting game with 2 human players.
 */
public class PlayerVsPlayer implements CommandExecutor {

    @Override
    public void execute() {
        HumanPlayer player1 = new HumanPlayer(1);
        HumanPlayer player2 = new HumanPlayer(2);
        GameServer gameServer = new GameServer(player1, player2);
        int res = gameServer.play();
        if (res == 0) {
            System.out.println("\nGame has ended in a draw!");
        } else if (res == 1) {
            System.out.println("\nPlayer with X has won!");
        } else {
            System.out.println("\nPlayer with O has won!");
        }
    }

}
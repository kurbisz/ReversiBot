package com.kurbisz.parser.commands;

import com.kurbisz.GameServer;
import com.kurbisz.heuristics.MainHeuristic;
import com.kurbisz.player.BotPlayer;
import com.kurbisz.player.HumanPlayer;

/**
 * Command responsible for starting game with 1 human and 1 bot.
 */
public class PlayerVsBot implements CommandExecutor {

    protected int depth;

    /**
     * Initialize parameters for game.
     * @param depth depth of minimax bot
     */
    public PlayerVsBot(int depth) {
        this.depth = depth;
    }

    @Override
    public void execute() {
        int n = 8;

        HumanPlayer player1 = new HumanPlayer(1);
        BotPlayer botPlayer = new BotPlayer(2, depth, new MainHeuristic(2, n), true);
        GameServer gameServer = new GameServer(player1, botPlayer);
        int res = gameServer.play();
        if (res == 0) {
            System.out.println("\nGame has ended in a draw!");
        } else if (res == 1) {
            System.out.println("\nYou have won!");
        } else {
            System.out.println("\nComputer has won!");
        }

    }

}
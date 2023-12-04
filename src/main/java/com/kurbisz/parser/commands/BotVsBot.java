package com.kurbisz.parser.commands;

import com.kurbisz.GameServer;
import com.kurbisz.genetics.HeuristicData;
import com.kurbisz.heuristics.MainHeuristic;
import com.kurbisz.player.BotPlayer;

/**
 * Command responsible for starting game with 2 bots.
 */
public class BotVsBot implements CommandExecutor {

    protected int depth1, depth2;

    /**
     * Initialize parameters for game.
     * @param depth1 depth of first bot
     * @param depth2 depth of second bot
     */
    public BotVsBot(int depth1, int depth2) {
        this.depth1 = depth1;
        this.depth2 = depth2;
    }

    @Override
    public void execute() {
        int n = 8;
        BotPlayer botPlayer1 = new BotPlayer(1, depth1, new MainHeuristic(1, n), true);
        BotPlayer botPlayer2 = new BotPlayer(2, depth2, new MainHeuristic(2, n), true);
        GameServer gameServer = new GameServer(botPlayer1, botPlayer2);
        int res = gameServer.play();
        if (res == 0) {
            System.out.println("\nGame has ended in a draw!");
        } else if (res == 1) {
            System.out.println("\nBot number 1 (X) has won!");
        } else {
            System.out.println("\nBot number 2 (O) has won!");
        }
    }
}
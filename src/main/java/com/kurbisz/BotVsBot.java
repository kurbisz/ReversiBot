package com.kurbisz;

import com.kurbisz.heuristics.SimpleHeuristic;
import com.kurbisz.player.BotPlayer;
import com.kurbisz.player.HumanPlayer;

public class BotVsBot {
    public static void main(String[] args) {
        int n = 8;

        BotPlayer botPlayer1 = new BotPlayer(1, 3, new SimpleHeuristic(1, n), true);
        BotPlayer botPlayer2 = new BotPlayer(2, 3, new SimpleHeuristic(2, n), true);
        GameServer gameServer = new GameServer(botPlayer1, botPlayer2);
        gameServer.play();
    }
}
package com.kurbisz;

import com.kurbisz.heuristics.SimpleHeuristic;
import com.kurbisz.player.BotPlayer;
import com.kurbisz.player.HumanPlayer;

public class PlayerVsBot {
    public static void main(String[] args) {
        int n = 8;

        HumanPlayer player1 = new HumanPlayer(1);
        BotPlayer botPlayer = new BotPlayer(2, 3, new SimpleHeuristic(2, n), true);
        GameServer gameServer = new GameServer(player1, botPlayer);
        gameServer.play();
    }
}
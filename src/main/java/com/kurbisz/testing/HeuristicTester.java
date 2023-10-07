package com.kurbisz.testing;

import com.kurbisz.player.BotPlayer;
import com.kurbisz.GameServer;
import com.kurbisz.heuristics.Heuristic;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HeuristicTester {

    private Random r = new Random();
    private Heuristic heuristic1, heuristic2;

    public HeuristicTester(Heuristic heuristic1, Heuristic heuristic2) {
        this.heuristic1 = heuristic1;
        this.heuristic2 = heuristic2;
    }

    public void fight(int depth) {

        heuristic1.playerNumber = 1;
        BotPlayer g1 = new BotPlayer(1, depth, heuristic1, false);
        heuristic2.playerNumber = 2;
        BotPlayer g2 = new BotPlayer(2, depth, heuristic2, false);

        GameServer gameServer = new GameServer(g1, g2);
        int res = gameServer.play();
        System.out.println("GAME 1: " + res);

        heuristic1.playerNumber = 2;
        g1 = new BotPlayer(2, depth, heuristic1, false);
        heuristic2.playerNumber = 1;
        g2 = new BotPlayer(1, depth, heuristic2, false);

        GameServer gameServer2 = new GameServer(g2, g1);
        int res2 = gameServer2.play();
        System.out.println("GAME 2: " + res2);


    }

}

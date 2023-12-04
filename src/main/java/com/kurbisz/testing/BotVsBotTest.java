package com.kurbisz.testing;

import com.kurbisz.GameServer;
import com.kurbisz.genetics.HeuristicData;
import com.kurbisz.heuristics.MainHeuristic;
import com.kurbisz.player.BotPlayer;

public class BotVsBotTest {
    public static void main(String[] args) {
        int n = 8;
        int depth = 3;
        boolean logs = true;
        HeuristicData heuristicData = HeuristicData.fromString(1,n,"32077, 45880, 36425, -65731, 68535, -58331, 7079, 7569, -97795, 89159, -67179, -93559, 10136, 54640, -58383, -37047, 79968, -70418, 		3723, 72625, 29979, 71566, 56384, -38033, 59110, 47042, -4681, 39982, 9278, 93981, 67256, 53868, 32236, -84422, 81316, 25577, 		74635, 88637, 83221, -5701, 7415, -75919, 73618, -65793, -38380, 24721, -76689, -72087, -31752, 72546, 93951, -28419, 46326, 15485, 		 (1,45) - [47, 16, 5, 38, 28, 15, 56, 46, 31, 25, 42, 7, 4, 10, 12, 2, 0, 52, 41, 36, 14, 18, 34, 43, 59, 51, 21, 22, 44, 9, 40, 60, 55, 45, 30, 20, 49, 6, 13, 8, 35, 1, 26, 39, 37, 61, 33, 58, 54, 57, 32, 11, 27, 23, 63, 53, 19, 48, 29, 17, 50, 62, 24, 3]");
        HeuristicData heuristicData2 = HeuristicData.fromString(2,n,"50039, 45975, -25016, -54101, 68001, 1188, 61656, -25880, -3975, -27597, 93547, 73802, -5400, -32474, 26218, 32827, -66150, -57086, \t\t7311, -40286, 81790, -60506, -91736, 45473, 51226, 38041, -20725, 57975, 40648, -5572, 4375, -80060, -39408, -9442, 4962, 42925, \t\t-96477, 12078, -30908, -89898, -61006, 94155, -91201, 28876, 97129, 52408, 32010, 15301, -30772, 76139, 63800, 68542, 14380, 72068, \t\t (1,45) - [29, 23, 62, 5, 60, 27, 46, 53, 42, 37, 63, 30, 32, 47, 3, 1, 57, 61, 43, 8, 19, 48, 26, 59, 21, 17, 31, 12, 50, 58, 4, 39, 38, 7, 34, 56, 51, 0, 15, 25, 22, 41, 44, 9, 24, 18, 14, 55, 40, 33, 11, 45, 10, 35, 28, 16, 36, 20, 6, 49, 54, 13, 52, 2]");
        test(heuristicData.heuristic, heuristicData2.heuristic, depth, logs);
    }

    private static void test(MainHeuristic mainHeuristic1, MainHeuristic mainHeuristic2, int depth, boolean logs) {
        mainHeuristic1.playerNumber = 1;
        BotPlayer botPlayer1 = new BotPlayer(1, depth, mainHeuristic1, logs);
        mainHeuristic2.playerNumber = 2;
        BotPlayer botPlayer2 = new BotPlayer(2, depth, mainHeuristic2, logs);
        GameServer gameServer = new GameServer(botPlayer1, botPlayer2);
        int res = gameServer.play();
        System.out.println("bot 1 vs bot 2: " + res);


        mainHeuristic1.playerNumber = 2;
        botPlayer1 = new BotPlayer(2, depth, mainHeuristic1, logs);
        mainHeuristic2.playerNumber = 1;
        botPlayer2 = new BotPlayer(1, depth, mainHeuristic2, logs);
        gameServer = new GameServer(botPlayer2, botPlayer1);
        res = gameServer.play();
        System.out.println("bot 2 vs bot 1: " + res);
    }
}
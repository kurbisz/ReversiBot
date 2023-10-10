package com.kurbisz;

import com.kurbisz.genetics.GeneticAlgorithm;
import com.kurbisz.genetics.HeuristicData;
import com.kurbisz.heuristics.SimpleHeuristic;
import com.kurbisz.player.BotPlayer;

public class BotVsNBotTest {

    private static int nr = 100;

    public static void main(String[] args) {
        int n = 8, depth = 3;
        HeuristicData heuristicData = HeuristicData.fromString(1,n,"91557, -88672, -93415, 43108, -69685, 78212, -54469, -59169, -2894, -81361, -94956, -15182, -76663, -42337, -68407, 36827, 85412, 55737, \t\t67675, -91450, -90923, 12770, -65327, 91059, -31453, 39277, -2619, 18998, -59478, -63186, -21623, 2392, -99442, 12973, -49292, 9883, \t\t-38819, 63671, -69428, -93413, 9665, 50126, -53807, -71152, -95842, 81280, -84444, -75920, -96818, -36587, -90542, 5681, -13386, -88479, \t\t (4,37) - [34, 32, 30, 59, 62, 1, 24, 5, 19, 27, 25, 37, 26, 11, 28, 0, 18, 49, 43, 22, 12, 35, 47, 42, 63, 10, 2, 8, 3, 55, 33, 9, 50, 56, 31, 46, 58, 57, 6, 44, 16, 15, 29, 61, 13, 20, 36, 52, 39, 21, 7, 38, 4, 23, 41, 17, 54, 53, 60, 40, 14, 45, 48, 51]");
        test(heuristicData, depth);
    }

    private static void test(HeuristicData heuristicData, int depth) {
        heuristicData.heuristic.playerNumber = 1;
        int wins = 0;
        for (int i = 0; i < nr; i++) {
            BotPlayer botPlayer1 = new BotPlayer(1, depth, heuristicData.heuristic, false);
            HeuristicData heuristicData2 = GeneticAlgorithm.getRandom(2);
            BotPlayer botPlayer2 = new BotPlayer(2, 3, heuristicData2.heuristic, false);
            GameServer gameServer = new GameServer(botPlayer1, botPlayer2);
            int res = gameServer.play();
            if (res == 1) wins++;
        }
        System.out.println("bot 1 vs bots: " + wins + "/" + nr + " wins");

        heuristicData.heuristic.playerNumber = 2;
        wins = 0;
        for (int i = 0; i < nr; i++) {
            BotPlayer botPlayer1 = new BotPlayer(2, depth, heuristicData.heuristic, false);
            HeuristicData heuristicData2 = GeneticAlgorithm.getRandom(1);
            BotPlayer botPlayer2 = new BotPlayer(1, 3, heuristicData2.heuristic, false);
            GameServer gameServer = new GameServer(botPlayer2, botPlayer1);
            int res = gameServer.play();
            if (res == 2) wins++;
        }
        System.out.println("bots vs bot 1: " + wins + "/" + nr + " wins");

    }
}
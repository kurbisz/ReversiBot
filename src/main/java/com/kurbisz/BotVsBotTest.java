package com.kurbisz;

import com.kurbisz.genetics.HeuristicData;
import com.kurbisz.heuristics.SimpleHeuristic;
import com.kurbisz.player.BotPlayer;

public class BotVsBotTest {
    public static void main(String[] args) {
        int n = 8;
        HeuristicData heuristicData = HeuristicData.fromString(1,n,"91557, -88672, -93415, 43108, -69685, 78212, -54469, -59169, -2894, -81361, -94956, -15182, -76663, -42337, -68407, 36827, 85412, 55737, \t\t67675, -91450, -90923, 12770, -65327, 91059, -31453, 39277, -2619, 18998, -59478, -63186, -21623, 2392, -99442, 12973, -49292, 9883, \t\t-38819, 63671, -69428, -93413, 9665, 50126, -53807, -71152, -95842, 81280, -84444, -75920, -96818, -36587, -90542, 5681, -13386, -88479, \t\t (4,37) - [34, 32, 30, 59, 62, 1, 24, 5, 19, 27, 25, 37, 26, 11, 28, 0, 18, 49, 43, 22, 12, 35, 47, 42, 63, 10, 2, 8, 3, 55, 33, 9, 50, 56, 31, 46, 58, 57, 6, 44, 16, 15, 29, 61, 13, 20, 36, 52, 39, 21, 7, 38, 4, 23, 41, 17, 54, 53, 60, 40, 14, 45, 48, 51]");
        HeuristicData heuristicData2 = HeuristicData.fromString(2,n,"23390, 88315, 19937, -76835, 44356, 23910, -85582, 76842, 71486, -67378, -100099, -32616, 37638, -42337, -60250, 43134, 70060, -100081, 		88138, -73361, 107493, -26328, 45871, 70746, 65710, -83420, -32819, 69774, 28992, 57214, 7083, -27236, -24351, 112307, -21873, -108747, 		-21968, -38014, -53641, 28974, 627, 46249, -54616, 67078, -65885, 94921, -46411, 108269, -53438, -93441, 56923, -34397, -12519, -84119, 		 (3,37) - [46, 50, 6, 63, 43, 9, 30, 25, 59, 31, 60, 27, 49, 32, 7, 34, 62, 12, 10, 16, 23, 2, 51, 40, 56, 44, 4, 35, 5, 29, 17, 3, 37, 20, 57, 14, 58, 42, 41, 47, 36, 15, 53, 18, 52, 13, 61, 19, 11, 22, 28, 55, 54, 38, 48, 45, 21, 26, 33, 1, 8, 39, 24, 0]");
        test(heuristicData.heuristic, heuristicData2.heuristic);
    }

    private static void test(SimpleHeuristic simpleHeuristic1, SimpleHeuristic simpleHeuristic2) {
        simpleHeuristic1.playerNumber = 1;
        BotPlayer botPlayer1 = new BotPlayer(1, 3, simpleHeuristic1, false);
        simpleHeuristic2.playerNumber = 2;
        BotPlayer botPlayer2 = new BotPlayer(2, 3, simpleHeuristic2, false);
        GameServer gameServer = new GameServer(botPlayer1, botPlayer2);
        int res = gameServer.play();
        System.out.println("bot 1 vs bot 2: " + res);


        simpleHeuristic1.playerNumber = 2;
        botPlayer1 = new BotPlayer(2, 3, simpleHeuristic1, false);
        simpleHeuristic2.playerNumber = 1;
        botPlayer2 = new BotPlayer(1, 3, simpleHeuristic2, false);
        gameServer = new GameServer(botPlayer2, botPlayer1);
        res = gameServer.play();
        System.out.println("bot 2 vs bot 1: " + res);
    }
}
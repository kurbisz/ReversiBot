package com.kurbisz.testing;

import com.kurbisz.GameServer;
import com.kurbisz.genetics.HeuristicData;
import com.kurbisz.player.BotPlayer;
import com.kurbisz.utils.MainHeuristicUtils;

public class BotVsNBotTest {

    private static int nr = 100;

    public static void main(String[] args) {
        int n = 8, depth = 3;
        HeuristicData heuristicData = HeuristicData.fromString(1,n,"82716, 84875, 76523, 21644, -903, 69339, -83659, -66763, 56386, -48045, 56564, 10376, -66873, -65773, -47031, -763, 44692, -34096, \t\t-52800, -8369, 57058, 50459, 25623, -71161, -99741, -24308, 92573, -84282, 17340, -86069, -13706, 86966, -82033, 65210, 59707, -93554, \t\t18101, -98174, -39618, 38760, -10805, -32327, 34091, 18688, 44516, 24621, 68105, -26549, 95452, 16400, 33824, 38322, 65292, -79872, \t\t (25,72) - [59, 17, 49, 42, 30, 11, 60, 45, 51, 35, 29, 33, 62, 44, 10, 0, 52, 41, 9, 46, 34, 20, 61, 5, 39, 36, 31, 32, 15, 21, 4, 28, 24, 16, 54, 14, 12, 7, 38, 2, 26, 3, 47, 58, 22, 55, 27, 37, 50, 40, 8, 25, 23, 43, 57, 1, 18, 48, 53, 19, 56, 63, 13, 6]");
        test(heuristicData, depth);
    }

    private static void test(HeuristicData heuristicData, int depth) {
        heuristicData.heuristic.playerNumber = 1;
        int wins = 0;
        for (int i = 0; i < nr; i++) {
            BotPlayer botPlayer1 = new BotPlayer(1, depth, heuristicData.heuristic, false);
            HeuristicData heuristicData2 = MainHeuristicUtils.getRandom(2);
            BotPlayer botPlayer2 = new BotPlayer(2, depth, heuristicData2.heuristic, false);
            GameServer gameServer = new GameServer(botPlayer1, botPlayer2);
            int res = gameServer.play();
            if (res == 1) wins++;
        }
        System.out.println("bot 1 vs bots: " + wins + "/" + nr + " wins");

        heuristicData.heuristic.playerNumber = 2;
        wins = 0;
        for (int i = 0; i < nr; i++) {
            BotPlayer botPlayer1 = new BotPlayer(2, depth, heuristicData.heuristic, false);
            HeuristicData heuristicData2 = MainHeuristicUtils.getRandom(1);
            BotPlayer botPlayer2 = new BotPlayer(1, depth, heuristicData2.heuristic, false);
            GameServer gameServer = new GameServer(botPlayer2, botPlayer1);
            int res = gameServer.play();
            if (res == 2) wins++;
        }
        System.out.println("bots vs bot 1: " + wins + "/" + nr + " wins");

    }
}
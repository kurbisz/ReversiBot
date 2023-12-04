package com.kurbisz.testing;

import com.kurbisz.GameServer;
import com.kurbisz.minimax.AlphaBeta;
import com.kurbisz.genetics.HeuristicData;
import com.kurbisz.player.BotPlayer;
import com.kurbisz.utils.MainHeuristicUtils;

public class NBotVsNBotTest {

    private static int nr = 10000;

    public static void main(String[] args) {
        int n = 8, depth = 3;
        test(depth);
    }

    private static void test(int depth) {
        for (int i = 0; i < nr; i++) {
            HeuristicData heuristicData1 = MainHeuristicUtils.getRandom(1);
            BotPlayer botPlayer1 = new BotPlayer(1, depth, heuristicData1.heuristic, false);
            HeuristicData heuristicData2 = MainHeuristicUtils.getRandom(2);
            BotPlayer botPlayer2 = new BotPlayer(2, depth, heuristicData2.heuristic, false);
            GameServer gameServer = new GameServer(botPlayer1, botPlayer2);
            gameServer.play();
        }

        System.out.println("MAX_AM: " + AlphaBeta.MAX_AM);
        System.out.println("GAME_AM: " + GameServer.GAME_AM);
        System.out.println("GAME_LENGTH: " + GameServer.GAME_LENGTH);
        System.out.println("GAME_LENGTH 2: " + (double) GameServer.GAME_LENGTH / GameServer.GAME_AM);
        System.out.println("MIN_GAME_LENGTH: " + GameServer.MIN_GAME_LENGTH);
        System.out.println("MAX_GAME_LENGTH_AM: " + GameServer.MAX_GAME_LENGTH_AM);
        System.out.println("MAX_GAME_LENGTH_AM 2: " + (double) GameServer.MAX_GAME_LENGTH_AM / GameServer.GAME_AM);


    }
}
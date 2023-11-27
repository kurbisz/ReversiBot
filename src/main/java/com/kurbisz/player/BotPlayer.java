package com.kurbisz.player;

import com.kurbisz.Board;
import com.kurbisz.algorithm.Algorithm;
import com.kurbisz.algorithm.AlphaBeta;
import com.kurbisz.heuristics.Heuristic;

public class BotPlayer extends Player {


    private int depth;
    private Heuristic heuristic;
    private boolean logs;

    public BotPlayer(int playerNumber, int depth, Heuristic heuristic, boolean logs) {
        super(playerNumber);
        this.playerNumber = playerNumber;
        this.depth = depth;
        this.heuristic = heuristic;
        this.logs = logs;
    }

    @Override
    public int play(Board b) {
        if (logs) b.printBoard();
        Algorithm a = new AlphaBeta(b.n, playerNumber, depth, heuristic);

//        long begin = System.nanoTime();
        int minMaxMove = a.getMove(b.fields);
//        synchronized (STATISTICS_OVERALL_TIME_OBJ) {
//            STATISTICS_OVERALL_TIME += System.nanoTime() - begin;
//        }
        return minMaxMove;
    }


}

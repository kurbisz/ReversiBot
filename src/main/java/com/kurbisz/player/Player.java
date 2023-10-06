package com.kurbisz.player;

import com.kurbisz.Board;
import com.kurbisz.algorithm.Algorithm;
import com.kurbisz.heuristics.Heuristic;

public abstract class Player {

    public static long STATISTICS_OVERALL_TIME = 0;

    protected int playerNumber;
    protected int n = 8;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public abstract int play(Board b);

}

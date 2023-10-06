package com.kurbisz.heuristics;

public class ZeroHeuristic extends Heuristic {


    public ZeroHeuristic(int playerNumber, int n) {
        super(playerNumber, n);
    }

    @Override
    public int getEvaluation(byte[] field) {
        return 0;
    }
}

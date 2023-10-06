package com.kurbisz.heuristics;

import com.kurbisz.Utils;

public class OutsideHeuristic extends Heuristic {

    private int coefficients[] = {1000,100,10,100,1000,100,10,1,10,100,10,1,-1000,1,10,100,10,1,10,100,1000,100,10,100,1000};

    public OutsideHeuristic(int playerNumber, int n) {
        super(playerNumber, n);
    }

    @Override
    public int getEvaluation(byte[] field) {
        int eval = 0;
        for (int i = 0; i < n * n; i++) {
            if (field[i] == playerNumber) eval += coefficients[i];
            else if (field[i] == 3 - playerNumber) eval -= coefficients[i];
        }
        return eval;
    }


}

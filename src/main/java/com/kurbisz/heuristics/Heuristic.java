package com.kurbisz.heuristics;

import com.kurbisz.Utils;

import java.util.Arrays;
import java.util.HashMap;

public abstract class Heuristic {

    public int playerNumber, n;

    public int[] randomArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32,
            33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63};

    public Heuristic(int playerNumber, int n) {
        this.playerNumber = playerNumber;
        this.n = n;
    }

    public abstract int getEvaluation(byte[] field);

    public int getEndAdvantage(byte[] field) {
        return getEndAdvantage(field, false);
    }

    public int getEndAdvantage(byte[] field, boolean isRoot) {
        if (Utils.isAnyMove(field, n, playerNumber) || Utils.isAnyMove(field, n, 3 - playerNumber)) {
            return 0;
        }
        int winner = Utils.getWinner(field, n);
        if (winner == 2) return 0;
        if (winner == playerNumber + 1) return Integer.MAX_VALUE;
        return Integer.MIN_VALUE;
    }
}

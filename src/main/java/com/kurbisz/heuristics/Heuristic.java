package com.kurbisz.heuristics;

import com.kurbisz.Utils;

import java.util.Arrays;
import java.util.HashMap;

public abstract class Heuristic {

//    protected HashMap<Long, Integer> cachedValues = new HashMap<>();

    public int playerNumber, n;

    public int[] randomArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
    protected static int win[][][] = {
            {{0, 0}, {0, 1}, {0, 2}, {0, 3}},
            {{1, 0}, {1, 1}, {1, 2}, {1, 3}},
            {{2, 0}, {2, 1}, {2, 2}, {2, 3}},
            {{3, 0}, {3, 1}, {3, 2}, {3, 3}},
            {{4, 0}, {4, 1}, {4, 2}, {4, 3}},
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}},
            {{1, 1}, {1, 2}, {1, 3}, {1, 4}},
            {{2, 1}, {2, 2}, {2, 3}, {2, 4}},
            {{3, 1}, {3, 2}, {3, 3}, {3, 4}},
            {{4, 1}, {4, 2}, {4, 3}, {4, 4}},
            {{0, 0}, {1, 0}, {2, 0}, {3, 0}},
            {{0, 1}, {1, 1}, {2, 1}, {3, 1}},
            {{0, 2}, {1, 2}, {2, 2}, {3, 2}},
            {{0, 3}, {1, 3}, {2, 3}, {3, 3}},
            {{0, 4}, {1, 4}, {2, 4}, {3, 4}},
            {{1, 0}, {2, 0}, {3, 0}, {4, 0}},
            {{1, 1}, {2, 1}, {3, 1}, {4, 1}},
            {{1, 2}, {2, 2}, {3, 2}, {4, 2}},
            {{1, 3}, {2, 3}, {3, 3}, {4, 3}},
            {{1, 4}, {2, 4}, {3, 4}, {4, 4}},
            {{0, 1}, {1, 2}, {2, 3}, {3, 4}},
            {{0, 0}, {1, 1}, {2, 2}, {3, 3}},
            {{1, 1}, {2, 2}, {3, 3}, {4, 4}},
            {{1, 0}, {2, 1}, {3, 2}, {4, 3}},
            {{0, 3}, {1, 2}, {2, 1}, {3, 0}},
            {{0, 4}, {1, 3}, {2, 2}, {3, 1}},
            {{1, 3}, {2, 2}, {3, 1}, {4, 0}},
            {{1, 4}, {2, 3}, {3, 2}, {4, 1}}
    };

    protected static int lose[][][] = {
            {{0, 0}, {0, 1}, {0, 2}}, {{0, 1}, {0, 2}, {0, 3}}, {{0, 2}, {0, 3}, {0, 4}},
            {{1, 0}, {1, 1}, {1, 2}}, {{1, 1}, {1, 2}, {1, 3}}, {{1, 2}, {1, 3}, {1, 4}},
            {{2, 0}, {2, 1}, {2, 2}}, {{2, 1}, {2, 2}, {2, 3}}, {{2, 2}, {2, 3}, {2, 4}},
            {{3, 0}, {3, 1}, {3, 2}}, {{3, 1}, {3, 2}, {3, 3}}, {{3, 2}, {3, 3}, {3, 4}},
            {{4, 0}, {4, 1}, {4, 2}}, {{4, 1}, {4, 2}, {4, 3}}, {{4, 2}, {4, 3}, {4, 4}},
            {{0, 0}, {1, 0}, {2, 0}}, {{1, 0}, {2, 0}, {3, 0}}, {{2, 0}, {3, 0}, {4, 0}},
            {{0, 1}, {1, 1}, {2, 1}}, {{1, 1}, {2, 1}, {3, 1}}, {{2, 1}, {3, 1}, {4, 1}},
            {{0, 2}, {1, 2}, {2, 2}}, {{1, 2}, {2, 2}, {3, 2}}, {{2, 2}, {3, 2}, {4, 2}},
            {{0, 3}, {1, 3}, {2, 3}}, {{1, 3}, {2, 3}, {3, 3}}, {{2, 3}, {3, 3}, {4, 3}},
            {{0, 4}, {1, 4}, {2, 4}}, {{1, 4}, {2, 4}, {3, 4}}, {{2, 4}, {3, 4}, {4, 4}},
            {{0, 2}, {1, 3}, {2, 4}}, {{0, 1}, {1, 2}, {2, 3}}, {{1, 2}, {2, 3}, {3, 4}},
            {{0, 0}, {1, 1}, {2, 2}}, {{1, 1}, {2, 2}, {3, 3}}, {{2, 2}, {3, 3}, {4, 4}},
            {{1, 0}, {2, 1}, {3, 2}}, {{2, 1}, {3, 2}, {4, 3}}, {{2, 0}, {3, 1}, {4, 2}},
            {{0, 2}, {1, 1}, {2, 0}}, {{0, 3}, {1, 2}, {2, 1}}, {{1, 2}, {2, 1}, {3, 0}},
            {{0, 4}, {1, 3}, {2, 2}}, {{1, 3}, {2, 2}, {3, 1}}, {{2, 2}, {3, 1}, {4, 0}},
            {{1, 4}, {2, 3}, {3, 2}}, {{2, 3}, {3, 2}, {4, 1}}, {{2, 4}, {3, 3}, {4, 2}}
    };


    protected static int formattedWin[][], formattedLose[][];

    public Heuristic(int playerNumber, int n) {
        this.playerNumber = playerNumber;
        this.n = n;
        init();
    }

    public abstract int getEvaluation(byte[] field);

    protected boolean isWin(byte[] field, int player) {
//        long longField = Utils.fieldToInt(field, n, player);
//        Integer eval = cachedValues.get(longField);
//        if (eval != null) {
//            if (eval == Integer.MAX_VALUE) return true;
//            else return false;
//        }
        for (int i = 0; i < 28; i++) {
            int isWin = 0;
            for (int j = 0; j < 4; j++) {
                if (field[formattedWin[i][j]] == player)
                    isWin++;
            }
            if (isWin == 4) {
                return true;
            }
        }
        return false;
    }

    protected boolean isLose(byte[] field, int player) {
//        long longField = Utils.fieldToInt(field, n, player);
//        Integer eval = cachedValues.get(longField);
//        if (eval != null) {
//            if (eval == Integer.MIN_VALUE) return true;
//            else return false;
//        }
        for (int i = 0; i < 48; i++) {
            int isLose = 0;
            for (int j = 0; j < 3; j++) {
                if (field[formattedLose[i][j]] == player)
                    isLose++;
            }
            if (isLose == 3) {
                return true;
            }
        }
        return false;
    }

    public int getEndAdvantage(byte[] field) {
        return getEndAdvantage(field, false);
    }

    public int getEndAdvantage(byte[] field, boolean isRoot) {
//        long longField = Utils.fieldToInt(field, n, playerNumber);
//        if (!isRoot) {
//            Integer eval = cachedValues.get(longField);
//            if (eval != null) {
//                return eval;
//            }
//        }

        int res = 0;
        if (isWin(field, playerNumber)) {
            res = Integer.MAX_VALUE;
        } else if (isLose(field, playerNumber) || isWin(field, 3 - playerNumber)) {
            res = Integer.MIN_VALUE;
        } else if (isLose(field, 3 - playerNumber)) {
            res = Integer.MAX_VALUE;
        }
//        if (res != 0)
//            cachedValues.put(longField, res);
        return res;
    }

    private void init() {
        formattedWin = new int[28][4];
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 4; j++) {
                formattedWin[i][j] = Utils.toIndexMove(win[i][j][1], win[i][j][0], n);
            }
        }

        formattedLose = new int[48][3];
        for (int i = 0; i < 48; i++) {
            for (int j = 0; j < 3; j++) {
                formattedLose[i][j] = Utils.toIndexMove(lose[i][j][1], lose[i][j][0], n);
            }
        }



    }

}

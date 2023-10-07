package com.kurbisz.heuristics;

import com.kurbisz.Utils;

public class SimpleHeuristic extends Heuristic {



    /*
        6. CenterFours:
            _O___
            _XX__
            _XXO_
            __O__
            ___O_

     */

    public static int[] stages = {12, 30};

    public static int coefficientAmount = 4;
    public int[][] coefficients;

    public SimpleHeuristic(int playerNumber, int n) {
        this(playerNumber, n, new int[][]{
                new int[]{1000, -1000, 5000, -5000},
                new int[]{1000, -1000, 5000, -5000},
                new int[]{1000, -1000, 5000, -5000}}, null);
    }


    public SimpleHeuristic(int playerNumber, int n,
                           int[][] coefficients,
                           int[] randomArray) {
        super(playerNumber, n);
        this.coefficients = coefficients;
        if (randomArray != null) {
            this.randomArr = randomArray;
        }
    }

    @Override
    public int getEvaluation(byte[] field) {


        int moveNr = 0;
        for (int i = 0; i < n*n; i++) {
            if (field[i] != 0) moveNr++;
        }

        int stage = 0;
        for (int i = 0; i < stages.length; i++) {
            if (moveNr >= stages[i]) {
                stage = i+1;
            }
        }

        int[] playersMoves = getPlayersMoves(field, playerNumber);
        int[] playersFields = getPlayersFields(field, playerNumber);

        int res = playersMoves[0] * coefficients[stage][0]
                + playersMoves[1] * coefficients[stage][1]
                + playersFields[0] * coefficients[stage][2]
                + playersFields[1] * coefficients[stage][3];

        return res;
    }

    private int[] getPlayersMoves(byte field[], int playerNumber) {
        int[] res = new int[2];
        for (int i = 0; i < n*n; i++) {
            if (Utils.isValidMove(field, n, playerNumber, i)) res[0]++;
            else if (Utils.isValidMove(field, n, 3 - playerNumber, i)) res[1]++;
        }
        return res;
    }

    private int[] getPlayersFields(byte field[], int playerNumber) {
        int[] res = new int[2];
        for (int i = 0; i < n*n; i++) {
            if (field[i] == playerNumber) res[0]++;
            else if (field[i] == 3 - playerNumber) res[1]++;
        }
        return res;
    }


}

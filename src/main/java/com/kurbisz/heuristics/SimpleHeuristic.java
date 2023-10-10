package com.kurbisz.heuristics;

import com.kurbisz.Utils;

import static java.lang.Math.max;

public class SimpleHeuristic extends Heuristic {



    /*
        0-1. Number of available player's/opponent's moves
        2-3. Number of fields taken by player/opponent
        4-5. Number of corners taken by player/opponent
        6-7. Number of squares 2x2 taken by player/opponent
        8-9. Number of squares 3x3 taken by player/opponent
        10-11. Number of whole lanes taken by player/opponent
        12-13. Maximum number of changed fields by 1 move of player/opponent
        14-17. Number of player's fields minus nr of opponent's fields in distance from center (dist = 14 - nr)
        18-19. Number of fields which are adjacent to corner on diagonal.
     */

    public static int[] stages = {12, 30};

    public static int coefficientAmount = 20;
    public int[][] coefficients;

    private static int[][] distanceFields = {{27,28,35,36}, {18,19,20,21,42,43,44,45,26,34,29,37}, {9,10,11,12,13,14,49,50,51,52,53,54,17,25,33,41,22,30,38,46},
            {0,1,2,3,4,5,6,7,56,57,58,59,60,61,62,63,8,16,24,32,40,48,15,23,31,39,47,55}};

    public SimpleHeuristic(int playerNumber, int n) {
        this(playerNumber, n, new int[][]{
                new int[]{1000, -1000, 5000, -5000, 20000, -20000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{1000, -1000, 5000, -5000, 20000, -20000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{1000, -1000, 5000, -5000, 20000, -20000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}, null);
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
        int[] playersCorners = getPlayersCorners(field, playerNumber);
        int[] playersSquares2 = getPlayersSquares(field, playerNumber, 2);
        int[] playersSquares3 = getPlayersSquares(field, playerNumber, 3);
        int[] playersLanes = getPlayersLanes(field, playerNumber);
        int[] playersMaxChangedFields = getMaxChangedFields(field, playerNumber);
        int[] distanceFields = getDistanceFields(field, playerNumber);
        int[] playersSecondCorners = getPlayersSecondCorners(field, playerNumber);

        int res = playersMoves[0] * coefficients[stage][0]
                + playersMoves[1] * coefficients[stage][1]
                + playersFields[0] * coefficients[stage][2]
                + playersFields[1] * coefficients[stage][3]
                + playersCorners[0] * coefficients[stage][4]
                + playersCorners[1] * coefficients[stage][5]
                + playersSquares2[0] * coefficients[stage][6]
                + playersSquares2[1] * coefficients[stage][7]
                + playersSquares3[0] * coefficients[stage][8]
                + playersSquares3[1] * coefficients[stage][9]
                + playersLanes[0] * coefficients[stage][10]
                + playersLanes[1] * coefficients[stage][11]
                + playersMaxChangedFields[0] * coefficients[stage][12]
                + playersMaxChangedFields[1] * coefficients[stage][13]
                + distanceFields[0] * coefficients[stage][14]
                + distanceFields[1] * coefficients[stage][15]
                + distanceFields[2] * coefficients[stage][16]
                + distanceFields[3] * coefficients[stage][17]
                + playersSecondCorners[0] * coefficients[stage][18]
                + playersSecondCorners[1] * coefficients[stage][19];

        return res;
    }

    private int[] getPlayersMoves(byte field[], int playerNumber) {
        int[] res = new int[2];
        for (int i = 0; i < n*n; i++) {
            if (Utils.isValidMove(field, n, playerNumber, i)) res[0]++;
            if (Utils.isValidMove(field, n, 3 - playerNumber, i)) res[1]++;
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

    private int[] getPlayersCorners(byte field[], int playerNumber) {
        int[] res = new int[2];

        if (field[0] == playerNumber) res[0]++;
        else if (field[0] == 3 - playerNumber) res[1]++;

        if (field[n-1] == playerNumber) res[0]++;
        else if (field[n-1] == 3 - playerNumber) res[1]++;

        if (field[n*(n-1)] == playerNumber) res[0]++;
        else if (field[n*(n-1)] == 3 - playerNumber) res[1]++;

        if (field[n*n-1] == playerNumber) res[0]++;
        else if (field[n*n-1] == 3 - playerNumber) res[1]++;

        return res;
    }

    private int[] getPlayersSecondCorners(byte field[], int playerNumber) {
        int[] res = new int[2];

        if (field[n+1] == playerNumber) res[0]++;
        else if (field[n+1] == 3 - playerNumber) res[1]++;

        if (field[2*n-2] == playerNumber) res[0]++;
        else if (field[2*n-2] == 3 - playerNumber) res[1]++;

        if (field[n*(n-2)+1] == playerNumber) res[0]++;
        else if (field[n*(n-2)+1] == 3 - playerNumber) res[1]++;

        if (field[n*(n-1)-2] == playerNumber) res[0]++;
        else if (field[n*(n-1)-2] == 3 - playerNumber) res[1]++;

        return res;
    }

    private int[] getPlayersSquares(byte field[], int playerNumber, int size) {
        int[] res = new int[2];
        for (int i = 0; i < n - size + 1; i++) {
            for (int j = 0; j < n - size + 1; j++) {
                int player = field[i + j * n];
                if (player == 0) continue;
                boolean finish = false;
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        if (field[i + k + (j + l) * n] != player) {
                            finish = true;
                            break;
                        }
                    }
                    if (finish) break;
                }
                if (finish) continue;
                if (player == playerNumber) res[0]++;
                else res[1]++;
            }
        }
        return res;
    }

    private int[] getPlayersLanes(byte field[], int playerNumber) {
        int[] res = new int[2];
        for (int i = 0; i < n; i++) {
            int player = field[i];
            if (player == 0) continue;
            boolean finish = false;
            for (int j = 0; j < n; j++) {
                if (field[i + j * n] != player) {
                    finish = true;
                    break;
                }
            }
            if (finish) continue;
            if (player == playerNumber) res[0]++;
            else res[1]++;
        }

        for (int j = 0; j < n; j++) {
            int player = field[j * n];
            if (player == 0) continue;
            boolean finish = false;
            for (int i = 0; i < n; i++) {
                if (field[i + j * n] != player) {
                    finish = true;
                    break;
                }
            }
            if (finish) continue;
            if (player == playerNumber) res[0]++;
            else res[1]++;
        }

        return res;
    }

    private int[] getMaxChangedFields(byte field[], int playerNumber) {
        int[] res = new int[2];
        for (int i = 0; i < n*n; i++) {
            int max1 = Utils.getChangedFields(field, n, playerNumber, i);
            res[0] = max(max1, res[0]);
            int max2 = Utils.getChangedFields(field, n, 3 - playerNumber, i);
            res[1] = max(max2, res[1]);
        }
        return res;
    }

    private int[] getDistanceFields(byte field[], int playerNumber) {
        int[] res = new int[4];
        for (int i = 0; i < 4; i++) {
            for (int f : distanceFields[i]) {
                if (field[f] == playerNumber) res[i]++;
                else if (field[f] == 3 - playerNumber) res[i]--;
            }
        }
        return res;
    }

    @Override
    public Heuristic clone() {
        return new SimpleHeuristic(playerNumber, n, coefficients, randomArr);
    }
}

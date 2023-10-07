package com.kurbisz.heuristics;

import com.kurbisz.Utils;

public class ex extends Heuristic {

    private static int pairsWithThrees[][][] = {
            {{0, 0}, {0, 1}, {0, 2}, {0, 3}}, {{0, 1}, {0, 2}, {0, 3}, {0, 4}},
            {{0, 4}, {0, 3}, {0, 2}, {0, 1}}, {{0, 3}, {0, 2}, {0, 1}, {0, 0}},
            {{1, 0}, {1, 1}, {1, 2}, {1, 3}}, {{1, 1}, {1, 2}, {1, 3}, {1, 4}},
            {{1, 4}, {1, 3}, {1, 2}, {1, 1}}, {{1, 3}, {1, 2}, {1, 1}, {1, 0}},
            {{2, 0}, {2, 1}, {2, 2}, {2, 3}}, {{2, 1}, {2, 2}, {2, 3}, {2, 4}},
            {{2, 4}, {2, 3}, {2, 2}, {2, 1}}, {{2, 3}, {2, 2}, {2, 1}, {2, 0}},
            {{3, 0}, {3, 1}, {3, 2}, {3, 3}}, {{3, 1}, {3, 2}, {3, 3}, {3, 4}},
            {{3, 4}, {3, 3}, {3, 2}, {3, 1}}, {{3, 3}, {3, 2}, {3, 1}, {3, 0}},
            {{4, 0}, {4, 1}, {4, 2}, {4, 3}}, {{4, 1}, {4, 2}, {4, 3}, {4, 4}},
            {{4, 4}, {4, 3}, {4, 2}, {4, 1}}, {{4, 3}, {4, 2}, {4, 1}, {4, 0}},
            // Diagonals
            {{0, 0}, {1, 1}, {2, 2}, {3, 3}}, {{0, 1}, {1, 2}, {2, 3}, {3, 4}},
            {{1, 0}, {2, 1}, {3, 2}, {4, 3}}, {{1, 1}, {2, 2}, {3, 3}, {4, 4}},
    };

    private static int diagonalThrees[][][] = {
            {{0, 2}, {1, 3}, {2, 4}}, {{2, 4}, {1, 3}, {0, 2}},
            {{2, 0}, {1, 1}, {0, 2}}, {{0, 2}, {1, 1}, {2, 0}}
    };

    private static int centerFours[][][] = {
            {{1, 1}, {1, 2}, {2, 1}, {2, 2}},
            {{1, 2}, {1, 3}, {2, 2}, {2, 3}},
            {{2, 1}, {2, 2}, {3, 1}, {3, 2}},
            {{2, 2}, {2, 3}, {3, 2}, {3, 3}}
    };

    private static int centerSquare[][] = {{2, 2}, {2, 4}, {4, 2}, {4, 4}};
    private static int centerDiamond[][] = {{2, 3}, {3, 2}, {3, 4}, {4, 3}};

    private static int layers[][] = {{12}, {11, 13, 7, 17}, {10, 6, 2, 8, 14, 16, 22, 18}, {1, 3, 5, 9, 15, 19, 21, 23}, {0, 4, 20, 24}};


    /*
        1. Pairs:
            _XX__
            _XX__
            ___O_
            ___O_
            _OO__

        2. Threes:
            _X___
            _XX_X
            O_OO_
            _X___
            _O_OO

        3. CenterSquare:
            _O___
            _X_X_
            _OO__
            _X_X_
            _____

        4. CenterDiamond:
            _O___
            __X__
            _X_X_
            __XO_
            ___O_

        5. PossibleMoves - amount of possible moves

        6. CenterFours:
            _O___
            _XX__
            _XXO_
            __O__
            ___O_

     */

    public static int[] stages = {10, 17};

    public static int coefficientAmount = 12;
    public int[] placeCoefficients;
    public int[][] coefficients;

    public ex(int playerNumber, int n) {
        this(playerNumber, n, new int[][]{
                new int[]{-1000, 1000, 700, -700, 100, -100, -2000, 2000, 400, -400, -1000, 1000},
                new int[]{-1000, 1000, 700, -700, 100, -100, -2000, 2000, 400, -400, -1000, 1000},
                new int[]{-1000, 1000, 700, -700, 100, -100, -2000, 2000, 400, -400, -1000, 1000}}, null);
    }

    public ex(int playerNumber, int n, boolean random) {
        this(playerNumber, n, new int[][]{
                new int[]{-1000, 1000, 700, -700, 100, -100, -2000, 2000, 400, -400, -1000, 1000},
                new int[]{-1000, 1000, 700, -700, 100, -100, -2000, 2000, 400, -400, -1000, 1000},
                new int[]{-1000, 1000, 700, -700, 100, -100, -2000, 2000, 400, -400, -1000, 1000}},
                random ? Utils.getRandomPermutation(n) : null);
    }

    public ex(int playerNumber, int n,
              int[][] coefficients,
              int[] randomArray) {
        this(playerNumber, n, coefficients, randomArray, new int[n]);
    }

    public ex(int playerNumber, int n,
              int[][] coefficients,
              int[] randomArray,
              int[] placeCoefficients) {
        super(playerNumber, n);
        this.coefficients = coefficients;
        this.placeCoefficients = placeCoefficients;
        if (randomArray != null) {
            this.randomArr = randomArray;
        }
    }

    @Override
    public int getEvaluation(byte[] field) {
//        long longField = Utils.fieldToInt(field, n, playerNumber);
//        Integer eval = getCachedMove(longField);
//        if (eval != null) return eval;

        int playersPairs[] = getPairs(field, playerNumber);
        int opponentPairs[] = getPairs(field, 3 - playerNumber);

        int placesEval = 0;
        for (int i = 0; i < n; i++) {
            for (int pos : layers[i]) {
                int val;
                if (field[pos] == playerNumber) val = 1;
                else val = -1;
                placesEval += val * placeCoefficients[i];
            }
        }

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

        int res = placesEval
                + playersPairs[1] * coefficients[stage][0]
                + opponentPairs[1] * coefficients[stage][1]
                + playersPairs[0] * coefficients[stage][2]
                + opponentPairs[0] * coefficients[stage][3]
                + getCenterSquares(field, playerNumber) * coefficients[stage][4]
                + getCenterSquares(field, 3 - playerNumber) * coefficients[stage][5]
                + getCenterDiamond(field, playerNumber) * coefficients[stage][6]
                + getCenterDiamond(field, 3 - playerNumber) * coefficients[stage][7]
                + getPossibleMoves(field, playerNumber) * coefficients[stage][8]
                + getPossibleMoves(field, 3 - playerNumber) * coefficients[stage][9]
                + getCenterFours(field, playerNumber) * coefficients[stage][10]
                + getCenterFours(field, 3 - playerNumber) * coefficients[stage][11];

//        cacheMove(longField, res);
        return res;
    }


    private int[] getPairs(byte[] field, int player) {
        int threes = 0, twos = 0;
        for (int[][] pair : pairsWithThrees) {
            boolean isThreeVert = true, isThreeHor = true;
            for (int i = 0; i < 2; i++) {
                if (field[Utils.toIndexMove(pair[i][1], pair[i][0], n)] != player) isThreeVert = false;
                if (field[Utils.toIndexMove(pair[i][0], pair[i][1], n)] != player) isThreeHor = false;

            }
            if (field[Utils.toIndexMove(pair[2][1], pair[2][0], n)] != 0) isThreeVert = false;
            if (isThreeVert) {
                if (field[Utils.toIndexMove(pair[3][1], pair[3][0], n)] != player) twos++;
                else threes++;
            }

            if (field[Utils.toIndexMove(pair[2][0], pair[2][1], n)] != 0) isThreeHor = false;
            if (isThreeHor) {
                if (field[Utils.toIndexMove(pair[3][0], pair[3][1], n)] != player) twos++;
                else threes++;
            }
        }
        for (int[][] pair : diagonalThrees) {
            boolean isTwoVert = true, isTwoHor = true;
            for (int i = 0; i < 2; i++) {
                if (field[Utils.toIndexMove(pair[i][1], pair[i][0], n)] != player) isTwoVert = false;
                if (field[Utils.toIndexMove(pair[i][0], pair[i][1], n)] != player) isTwoHor = false;
            }

            if (field[Utils.toIndexMove(pair[2][1], pair[2][0], n)] != 0) isTwoVert = false;
            if (field[Utils.toIndexMove(pair[2][0], pair[2][1], n)] != 0) isTwoHor = false;

            if (isTwoVert) twos++;
            if (isTwoHor) twos++;

        }
        return new int[]{threes, twos};
    }

    private int getCenterSquares(byte[] field, int player) {
        for (int[] pair : centerSquare) {
            if (field[Utils.toIndexMove(pair[0], pair[1], n)] != player) {
                return 0;
            }
        }
        return 1;
    }

    private int getCenterDiamond(byte[] field, int player) {
        for (int[] pair : centerDiamond) {
            if (field[Utils.toIndexMove(pair[0], pair[1], n)] != player) {
                return 0;
            }
        }
        return 1;
    }

    public int getPossibleMoves(byte[] field, int player) {
        int nr = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (field[Utils.toIndexMove(i, j, n)] == 0) {
                    field[Utils.toIndexMove(i, j, n)] = (byte) player;
//                    if (isWin(field, player) || !isLose(field, player)) {
//                        nr++;
//                    }
                    field[Utils.toIndexMove(i, j, n)] = 0;
                }
            }
        }
        return nr;
    }


    private int getCenterFours(byte[] field, int player) {
        for (int[][] four : centerFours) {
            boolean isFour = true;
            for (int[] pair : four) {
                if (field[Utils.toIndexMove(pair[0], pair[1], n)] != player) {
                    isFour = false;
                    break;
                }
            }
            if (isFour) {
                return 1;
            }
        }
        return 0;
    }

//    private void cacheMove(Long longField, int res) {
//        synchronized (SimpleHeuristic.class) {
//            cachedValues.put(longField, res);
//        }
//    }
//
//    private Integer getCachedMove(Long longField) {
//        synchronized (SimpleHeuristic.class) {
//            return cachedValues.get(longField);
//        }
//    }

}

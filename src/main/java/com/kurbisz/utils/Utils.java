package com.kurbisz.utils;

import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Static class with all operations connected to executing moves or checking properties on board.
 */
public final class Utils {

    /**
     * Checking if given move is valid on defined board.
     * @param field board in array format
     * @param n board size (default 8)
     * @param playerNr number of player to move
     * @param move index number of move
     * @return
     */
    public static boolean isValidMove(byte[] field, int n, int playerNr, int move) {
        if (field[move] != 0) return false;

        int x = move % n, y = move / n;
        if (checkBottom(field, n, playerNr, move, x, y) > 0) return true;
        if (checkTop(field, n, playerNr, move, x, y) > 0) return true;
        if (checkLeft(field, n, playerNr, move, x, y) > 0) return true;
        if (checkRight(field, n, playerNr, move, x, y) > 0) return true;
        if (checkRightBottom(field, n, playerNr, move, x, y) > 0) return true;
        if (checkLeftBottom(field, n, playerNr, move, x, y) > 0) return true;
        if (checkRightTop(field, n, playerNr, move, x, y) > 0) return true;
        if (checkLeftTop(field, n, playerNr, move, x, y) > 0) return true;

        return false;
    }

    /**
     * Checking if given move can be done looking only in bottom direction.
     * @param field board in array format
     * @param n board size (default 8)
     * @param playerNr number of player to move
     * @param move index number of move
     * @param x move's x position
     * @param y move's y position
     * @return number of opponent's pawns that will be changed
     */
    public static int checkBottom(byte[] field, int n, int playerNr, int move, int x, int y){
        if (move + 2 * n < n * n && field[move + n] == 3 - playerNr) {
            for (int i = move + 2 * n; i < n * n; i += n) {
                int val = field[i];
                if (val == 0) break;
                if (val == playerNr) {
                    return (i - move) / n - 1;
                }
            }
        }
        return -1;
    }

    /**
     * Checking if given move can be done looking only in top direction.
     * @param field board in array format
     * @param n board size (default 8)
     * @param playerNr number of player to move
     * @param move index number of move
     * @param x move's x position
     * @param y move's y position
     * @return number of opponent's pawns that will be changed
     */
    public static int checkTop(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (move - 2 * n >= 0 && field[move - n] == 3 - playerNr) {
            for (int i = move - 2 * n; i >= 0; i -= n) {
                int val = field[i];
                if (val == 0) break;
                if (val == playerNr) {
                    return (move - i) / n - 1;
                }
            }
        }
        return -1;
    }

    /**
     * Checking if given move can be done looking only in left direction.
     * @param field board in array format
     * @param n board size (default 8)
     * @param playerNr number of player to move
     * @param move index number of move
     * @param x move's x position
     * @param y move's y position
     * @return number of opponent's pawns that will be changed
     */
    public static int checkLeft(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (x >= 2 && field[move - 1] == 3 - playerNr) {
            for (int i = 2; i <= x; i++) {
                int val = field[move - i];
                if (val == 0) break;
                if (val == playerNr) {
                    return i - 1;
                }
            }
        }
        return -1;
    }

    /**
     * Checking if given move can be done looking only in right direction.
     * @param field board in array format
     * @param n board size (default 8)
     * @param playerNr number of player to move
     * @param move index number of move
     * @param x move's x position
     * @param y move's y position
     * @return number of opponent's pawns that will be changed
     */
    public static int checkRight(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (x < n - 2 && field[move + 1] == 3 - playerNr) {
            for (int i = 2; i + x < n; i++) {
                int val = field[move + i];
                if (val == 0) break;
                if (val == playerNr) {
                    return i - 1;
                }
            }
        }
        return -1;
    }

    /**
     * Checking if given move can be done looking only in left-bottom diagonal direction.
     * @param field board in array format
     * @param n board size (default 8)
     * @param playerNr number of player to move
     * @param move index number of move
     * @param x move's x position
     * @param y move's y position
     * @return number of opponent's pawns that will be changed
     */
    public static int checkLeftBottom(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (move + 2 * n < n * n && x >= 2 && field[move + n - 1] == 3 - playerNr) {
            for (int i = 2; i <= x && move + i * n < n * n; i++) {
                int val = field[move + (n-1) * i];
                if (val == 0) break;
                if (val == playerNr) {
                    return i - 1;
                }
            }
        }
        return -1;
    }

    /**
     * Checking if given move can be done looking only in left-top diagonal direction.
     * @param field board in array format
     * @param n board size (default 8)
     * @param playerNr number of player to move
     * @param move index number of move
     * @param x move's x position
     * @param y move's y position
     * @return number of opponent's pawns that will be changed
     */
    public static int checkLeftTop(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (move - 2 * n >= 0 && x >= 2 && field[move - n - 1] == 3 - playerNr) {
            for (int i = 2; i <= x && move - i * n >= 0; i++) {
                int val = field[move - (n+1) * i];
                if (val == 0) break;
                if (val == playerNr) {
                    return i - 1;
                }
            }
        }
        return -1;
    }

    /**
     * Checking if given move can be done looking only in right-top diagonal direction.
     * @param field board in array format
     * @param n board size (default 8)
     * @param playerNr number of player to move
     * @param move index number of move
     * @param x move's x position
     * @param y move's y position
     * @return number of opponent's pawns that will be changed
     */
    public static int checkRightTop(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (move - 2 * n >= 0 && x < n - 2 && field[move - n + 1] == 3 - playerNr) {
            for (int i = 2; i + x < n && move - i * n >= 0; i++) {
                int val = field[move + (1 - n) * i];
                if (val == 0) break;
                if (val == playerNr) {
                    return i - 1;
                }
            }
        }
        return -1;
    }

    /**
     * Checking if given move can be done looking only in right-bottom diagonal direction.
     * @param field board in array format
     * @param n board size (default 8)
     * @param playerNr number of player to move
     * @param move index number of move
     * @param x move's x position
     * @param y move's y position
     * @return number of opponent's pawns that will be changed
     */
    public static int checkRightBottom(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (move + 2 * n < n * n && x < n - 2 && field[move + n + 1] == 3 - playerNr) {
            for (int i = 2; i + x < n && move + i * n < n * n; i++) {
                int val = field[move + (1 + n) * i];
                if (val == 0) break;
                if (val == playerNr) {
                    return i - 1;
                }
            }
        }
        return -1;
    }


    /**
     * Convert field from readable format to index number.
     * @param move readable format of field (1st digit - row, 2nd digit - column)
     * @param n board size (default 8)
     * @return field in index format (from 0 to n*n-1)
     */
    public static int toIndexMove(int move, int n) {
        int x = (move % 10) - 1;
        int y = (move / 10) - 1;
        return toIndexMove(x, y, n);
    }

    /**
     * Convert field from x, y position to index number.
     * @param x field's x position
     * @param y field's y position
     * @param n board size (default 8)
     * @return field in index format (from 0 to n*n-1)
     */
    public static int toIndexMove(int x, int y, int n) {
        return x + y * n;
    }

    /**
     * Convert field from index number to readable format.
     * @param move index move field (from 0 to n*n-1)
     * @param n board size (default 8)
     * @return field in readable format (1st digit - row, 2nd digit - column)
     */
    public static int toNormalMove(int move, int n) {
        int x = move % n + 1;
        int y = move / n + 1;
        return x + y * 10;
    }

    /**
     * Clone board in array format to new byte array.
     * @param byteArray board in array format
     * @return clone of given byte array
     */
    public static byte[] cloneBoard(byte[] byteArray) {
        return byteArray.clone();
    }

    /**
     * Get random permutation of array with values from 0 to n*n-1.
     * @param n squared array size
     * @return array with random values from 0 to n*n-1 (each occuring once)
     */
    public static int[] getRandomPermutation(int n) {
        Random r = new Random();
        int[] randomArr = new int[n * n];
        for (int i = 0; i < n * n; i++)
            randomArr[i] = i;

        for (int i = randomArr.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            int temp = randomArr[i];
            randomArr[i] = randomArr[j];
            randomArr[j] = temp;
        }

        return randomArr;
    }

    /**
     * Get clone with similar array and make given amount of random element swaps.
     * @param act actual version of array
     * @param swaps amount of swaps to make
     * @return clone of 'act' array with made swaps
     */
    public static int[] getSimilarPermutation(int[] act, int swaps) {
        int n = act.length;
        Random r = new Random();
        int[] randomArr = new int[n];
        for (int i = 0; i < n; i++)
            randomArr[i] = act[i];

        for (int i = 0; i < swaps; i++) {
            int s1 = r.nextInt(n), s2 = r.nextInt(n);
            int tmp = randomArr[s1];
            randomArr[s1] = randomArr[s2];
            randomArr[s2] = tmp;
        }

        return randomArr;
    }

    /**
     * Execute given move and change proper pawns in all directions.
     * @param fields board in array format
     * @param move index number of move to be made
     * @param n board size (default 8)
     * @param playerNumber number of player (1 or 2)
     */
    public static void move(byte[] fields, int move, int n, byte playerNumber) {
        fields[move] = playerNumber;

        int x = move % n, y = move / n;
        if (Utils.checkBottom(fields, n, playerNumber, move, x, y) > 0) moveFields(fields, playerNumber, move, n);
        if (Utils.checkTop(fields, n, playerNumber, move, x, y) > 0) moveFields(fields, playerNumber, move, -n);
        if (Utils.checkLeft(fields, n, playerNumber, move, x, y) > 0) moveFields(fields, playerNumber, move, -1);
        if (Utils.checkRight(fields, n, playerNumber, move, x, y) > 0) moveFields(fields, playerNumber, move, 1);
        if (Utils.checkRightBottom(fields, n, playerNumber, move, x, y) > 0) moveFields(fields, playerNumber, move, n+1);
        if (Utils.checkLeftBottom(fields, n, playerNumber, move, x, y) > 0) moveFields(fields, playerNumber, move, n-1);
        if (Utils.checkRightTop(fields, n, playerNumber, move, x, y) > 0) moveFields(fields, playerNumber, move, -n+1);
        if (Utils.checkLeftTop(fields, n, playerNumber, move, x, y) > 0) moveFields(fields, playerNumber, move, -n-1);
    }

    /**
     * Move all fields in line.
     * @param fields board in array format
     * @param playerNumber number of player (1 or 2)
     * @param move index number of move to be made
     * @param add linear addition to field
     */
    private static void moveFields(byte[] fields, byte playerNumber, int move, int add) {
        for (int i = 1; fields[move + i * add] != playerNumber; i++) {
            fields[move + i * add] = playerNumber;
        }
    }

    /**
     * Check if there is any available move for given player.
     * @param fields board in array format
     * @param n board size (default 8)
     * @param playerNumber number of player (1 or 2)
     * @return
     */
    public static boolean isAnyMove(byte[] fields, int n, int playerNumber) {
        for (int i = 0; i < n * n; i++) {
            if (Utils.isValidMove(fields, n, playerNumber, i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculate result of game.
     * @param fields board in array format
     * @param n board size (default 8)
     * @return 0 when 1st player wins, 1 when 2nd player wins, 2 when there is a draw
     */
    public static int getWinner(byte[] fields, int n) {
        int[] numberOfPawns = new int[2];
        for (int i = 0; i < n*n; i++) {
            if (fields[i] != 0) {
                numberOfPawns[fields[i] - 1]++;
            }
        }
        if (numberOfPawns[0] > numberOfPawns[1]) return 0;
        if (numberOfPawns[0] < numberOfPawns[1]) return 1;
        return 2;
    }

    /**
     * Calculated amount of changed pawns after given move.
     * @param field board in array format
     * @param n board size (default 8)
     * @param playerNr number of player (1 or 2)
     * @param move index number of move to be made
     * @return amount of changed pawns
     */
    public static int getChangedFields(byte[] field, int n, int playerNr, int move) {
        int res = 0;

        int x = move % n, y = move / n;
        res += max(0, checkBottom(field, n, playerNr, move, x, y));
        res += max(0, checkTop(field, n, playerNr, move, x, y));
        res += max(0, checkLeft(field, n, playerNr, move, x, y));
        res += max(0, checkRight(field, n, playerNr, move, x, y));
        res += max(0, checkRightBottom(field, n, playerNr, move, x, y));
        res += max(0, checkLeftBottom(field, n, playerNr, move, x, y));
        res += max(0, checkRightTop(field, n, playerNr, move, x, y));
        res += max(0, checkLeftTop(field, n, playerNr, move, x, y));

        return res;
    }
}

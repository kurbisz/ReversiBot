package com.kurbisz;

import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Utils {

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



    public static int toIndexMove(int move, int n) {
        int x = (move % 10) - 1;
        int y = (move / 10) - 1;
        return toIndexMove(x, y, n);
    }

    public static int toIndexMove(int x, int y, int n) {
        return x + y * n;
    }

    public static int toNormalMove(int move, int n) {
        int x = move % n + 1;
        int y = move / n + 1;
        return x + y * 10;
    }

    public static byte[] cloneBoard(byte[] byteArray) {
        return byteArray.clone();
    }

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

    private static void moveFields(byte[] fields, byte playerNumber, int move, int add) {
        for (int i = 1; fields[move + i * add] != playerNumber; i++) {
            fields[move + i * add] = playerNumber;
        }
    }

    public static boolean isAnyMove(byte[] fields, int n, int playerNumber) {
        for (int i = 0; i < n * n; i++) {
            if (Utils.isValidMove(fields, n, playerNumber, i)) {
                return true;
            }
        }
        return false;
    }

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

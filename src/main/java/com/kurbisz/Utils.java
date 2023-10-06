package com.kurbisz;

import java.util.Random;

public class Utils {

    public static boolean isValidMove(byte[] field, int n, int playerNr, int move) {
        if (field[move] != 0) return false;

        int x = move % n, y = move / n;
        if (checkBottom(field, n, playerNr, move, x, y)) return true;
        if (checkTop(field, n, playerNr, move, x, y)) return true;
        if (checkLeft(field, n, playerNr, move, x, y)) return true;
        if (checkRight(field, n, playerNr, move, x, y)) return true;
        if (checkRightBottom(field, n, playerNr, move, x, y)) return true;
        if (checkLeftBottom(field, n, playerNr, move, x, y)) return true;
        if (checkRightTop(field, n, playerNr, move, x, y)) return true;
        if (checkLeftTop(field, n, playerNr, move, x, y)) return true;

        return false;
    }

    public static boolean checkBottom(byte[] field, int n, int playerNr, int move, int x, int y){
        if (move + 2 * n < n * n && field[move + n] == 3 - playerNr) {
            for (int i = move + 2 * n; i < n * n; i += n) {
                int val = field[i];
                if (val == 0) break;
                if (val == playerNr) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkTop(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (move - 2 * n >= 0 && field[move - n] == 3 - playerNr) {
            for (int i = move - 2 * n; i >= 0; i -= n) {
                int val = field[i];
                if (val == 0) break;
                if (val == playerNr) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkLeft(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (x >= 2 && field[move - 1] != playerNr) {
            for (int i = 2; i <= x; i++) {
                int val = field[move - i];
                if (val == 0) break;
                if (val == playerNr) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkRight(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (x < n - 2 && field[move + 1] == 3 - playerNr) {
            for (int i = 2; i + x < n; i++) {
                int val = field[move + i];
                if (val == 0) break;
                if (val == playerNr) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkLeftBottom(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (move + 2 * n < n * n && x >= 2 && field[move + n - 1] == 3 - playerNr) {
            for (int i = 2; i <= x && move + i * n < n * n; i++) {
                int val = field[move + (n-1) * i];
                if (val == 0) break;
                if (val == playerNr) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkLeftTop(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (move - 2 * n >= 0 && x >= 2 && field[move - n - 1] == 3 - playerNr) {
            for (int i = 2; i <= x && move - i * n < n * n; i++) {
                int val = field[move - (n+1) * i];
                if (val == 0) break;
                if (val == playerNr) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkRightTop(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (move - 2 * n >= 0 && x < n - 2 && field[move - n + 1] == 3 - playerNr) {
            for (int i = 2; i + x < n && move - i * n < n * n; i++) {
                int val = field[move + (1 - n) * i];
                if (val == 0) break;
                if (val == playerNr) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkRightBottom(byte[] field, int n, int playerNr, int move, int x, int y) {
        if (move + 2 * n < n * n && x < n - 2 && field[move + n + 1] == 3 - playerNr) {
            for (int i = 2; i + x < n && move + i * n < n * n; i++) {
                int val = field[move + (1 + n) * i];
                if (val == 0) break;
                if (val == playerNr) {
                    return true;
                }
            }
        }
        return false;
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

}

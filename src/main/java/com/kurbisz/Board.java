package com.kurbisz;

import com.kurbisz.utils.Utils;

/**
 * Class holding information about actual board and allowing to make basic game operations.
 */
public class Board {

    public int n = 8;
    public byte[] fields; // 0 - empty; 1 - X, 2 - O

    public Board() {
        fields = new byte[n * n];
    }

    /**
     * Change field on board to given player.
     * @param x field's x position (0-7)
     * @param y field's y position (0-7)
     * @param playerNumber number of player to set on given field
     */
    public void changeField(int x, int y, byte playerNumber) {
        fields[x + y * n] = playerNumber;
    }

    /**
     * Execute new player move to appropriate position.
     * @param move number of field
     * @param playerNumber number of player that made move
     */
    public void move(int move, byte playerNumber) {
        Utils.move(fields, move, n, playerNumber);
    }

    /**
     * Get actual player on given field.
     * @param x field's x position (0-7)
     * @param y field's y position (0-7)
     * @return number of player on given field, 0 when empty
     */
    public byte getPlayer(int x, int y) {
        return fields[x + y * n];
    }

    /**
     * Get actual player on given field.
     * @param ind index of field
     * @return number of player on given field, 0 when empty
     */
    public byte getPlayer(int ind) {
        return fields[ind];
    }

    /**
     * Print actual board state on default output.
     */
    public void printBoard() {
        System.out.println("  1 2 3 4 5 6 7 8");
        for (int i = 0; i < n; i++) {
            System.out.print("" + (i + 1));
            for (int j = 0; j < n; j++)
                switch (getPlayer(j, i)) {
                    case 0:
                        System.out.print(" -");
                        break;
                    case 1:
                        System.out.print(" X");
                        break;
                    case 2:
                        System.out.print(" O");
                        break;
                }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     * Calculate amount of all moves done in game.
     * @return amount of moves done on given board
     */
    public int getMoves() {
        int moves = -4;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (getPlayer(j, i) > 0) moves++;
            }
        }
        return moves;
    }

}

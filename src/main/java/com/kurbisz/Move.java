package com.kurbisz;

/**
 * Class holding information about fields and last move.
 */
public class Move {

    protected int n = 8;
    public byte[] field;
    public int lastMove;

    public Move(byte[] field, int lastMove) {
        this.field = field;
        this.lastMove = lastMove;
    }

    /**
     * Convert all fields to string separated by comma and add last move int as last element.
     * @return String representation of move
     */
    public String toString() {
        String str = "";
        for (byte b : field) {
            str += b + ", ";
        }
        return str + lastMove;
    }

    /**
     * Get player number on given field.
     * @param x number of column
     * @param y number of row
     * @return player on given field, 0 when empty
     */
    public byte getPlayer(int x, int y) {
        return field[x + y * n];
    }

    /**
     * Calculate amount of all moves done in game.
     * @return amount of moves done on actual fields
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

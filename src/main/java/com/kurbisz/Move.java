package com.kurbisz;

public class Move {

    public int n = 8;
    public byte[] field;
    public int lastMove;

    public Move(byte[] field, int lastMove) {
        this.field = field;
        this.lastMove = lastMove;
    }

    public String toString() {
        String str = "";
        for (byte b : field) {
            str += b + ", ";
        }
        return str;
    }

    public byte getPlayer(int x, int y) {
        return field[x + y * n];
    }

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

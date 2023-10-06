package com.kurbisz;

public class Move {

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

}

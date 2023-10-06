package com.kurbisz;

public class Board {

    public int n = 8;
    public byte[] fields; // 0 - empty; 1 - white, 2 - black

    public Board() {
        fields = new byte[n * n];
    }

    /**
     * Change field on board to given player.
     * @param x - x parameter (0-7)
     * @param y - y parameter (0-7)
     * @param playerNumber
     */
    public void changeField(int x, int y, byte playerNumber) {
        fields[x + y * n] = playerNumber;
    }

    public void move(int move, byte playerNumber) {
        fields[move] = playerNumber;
        int x = move % n, y = move / n;
        if (Utils.checkBottom(fields, n, playerNumber, move, x, y)) moveFields(playerNumber, move, n);
        if (Utils.checkTop(fields, n, playerNumber, move, x, y)) moveFields(playerNumber, move, -n);
        if (Utils.checkLeft(fields, n, playerNumber, move, x, y)) moveFields(playerNumber, move, -1);
        if (Utils.checkRight(fields, n, playerNumber, move, x, y)) moveFields(playerNumber, move, 1);
        if (Utils.checkRightBottom(fields, n, playerNumber, move, x, y)) moveFields(playerNumber, move, n+1);
        if (Utils.checkLeftBottom(fields, n, playerNumber, move, x, y)) moveFields(playerNumber, move, n-1);
        if (Utils.checkRightTop(fields, n, playerNumber, move, x, y)) moveFields(playerNumber, move, -n+1);
        if (Utils.checkLeftTop(fields, n, playerNumber, move, x, y)) moveFields(playerNumber, move, -n-1);
    }

    private void moveFields(byte playerNumber, int move, int add) {
        for (int i = 1; fields[move + i * add] != playerNumber; i++) {
            fields[move + i * add] = playerNumber;
        }
    }

    public byte getPlayer(int x, int y) {
        return fields[x + y * n];
    }

    public byte getPlayer(int ind) {
        return fields[ind];
    }

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

}

package com.kurbisz.player;

import com.kurbisz.Board;

/**
 * Abstract class for storing player and allowing him to make moves.
 */
public abstract class Player {

    protected int playerNumber;
    protected int n = 8;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * Get actual player's move based for given board.
     * @param b actual Board instance
     * @return move that has been done
     */
    public abstract int play(Board b);

    /**
     * Get player's number, 1 or 2 depending on who made first move.
     * @return number of player
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

}

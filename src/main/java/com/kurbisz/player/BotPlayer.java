package com.kurbisz.player;

import com.kurbisz.Board;
import com.kurbisz.minimax.MinimaxAlgorithm;
import com.kurbisz.minimax.AlphaBeta;
import com.kurbisz.heuristics.Heuristic;

/**
 * Implementation for bot player.
 */
public class BotPlayer extends Player {


    protected int depth;
    protected Heuristic heuristic;
    protected boolean logs;

    /**
     * Initialize parameters for bot gameplay.
     * @param playerNumber number of player
     * @param depth depth for Minimax algorithm
     * @param heuristic heuristic function
     * @param logs flag indicating if board should be printed
     */
    public BotPlayer(int playerNumber, int depth, Heuristic heuristic, boolean logs) {
        super(playerNumber);
        this.playerNumber = playerNumber;
        this.depth = depth;
        this.heuristic = heuristic;
        this.logs = logs;
    }

    /**
     * Execute Minimax algorithm with alpha-beta pruning and get best (known) move.
     * Print board if flag 'logs' has been set.
     *
     * @param b actual Board instance
     * @return move that has been computed
     */
    @Override
    public int play(Board b) {
        if (logs) b.printBoard();
        MinimaxAlgorithm a = new AlphaBeta(b.n, playerNumber, depth, heuristic);

        int minMaxMove = a.getMove(b.fields);

        return minMaxMove;
    }


}

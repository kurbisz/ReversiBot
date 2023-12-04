package com.kurbisz.minimax;

import com.kurbisz.Move;
import com.kurbisz.utils.Utils;
import com.kurbisz.heuristics.Heuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract class responsible for calculating allowed moves and choosing the best one.
 */
public abstract class MinimaxAlgorithm {

    protected int n, playerNumber, depth;
    protected Heuristic heuristic;
    protected Random r = new Random();

    public MinimaxAlgorithm(int n, int playerNumber, int depth, Heuristic heuristic) {
        this.n = n;
        this.playerNumber = playerNumber;
        this.depth = depth;
        this.heuristic = heuristic;
    }

    /**
     * Get move calculated by Minimax algorithm version.
     * @param field board in array format
     * @return index number of move
     */
    public abstract int getMove(byte[] field);

    /**
     * Get all possible moves that can be done by player.
     * @param move Move object with last move and board information
     * @param player actual player
     * @return list containing available moves
     */
    protected List<Move> getChildren(Move move, int player) {
        List<Move> list = new ArrayList<>();
        for (int i = 0; i < n * n; i++) {
            int ind = heuristic.randomArr[i];
            if (Utils.isValidMove(move.field, n, player, ind)) {
                byte[] newBoard = Utils.cloneBoard(move.field);
                Utils.move(newBoard, ind, n, (byte) player);
                list.add(new Move(newBoard, ind));
            }
        }
        return list;
    }

}

package com.kurbisz.algorithm;

import com.kurbisz.Move;
import com.kurbisz.Utils;
import com.kurbisz.heuristics.Heuristic;
import com.kurbisz.heuristics.HeuristicType;
import com.kurbisz.heuristics.SimpleHeuristic;
import com.kurbisz.heuristics.ZeroHeuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Algorithm {

    protected int n, playerNumber, depth;
    protected Heuristic heuristic;
    protected Random r = new Random();

    public Algorithm(int n, int playerNumber, int depth, Heuristic heuristic) {
        this.n = n;
        this.playerNumber = playerNumber;
        this.depth = depth;
        this.heuristic = heuristic;
    }

    public abstract int getMove(byte[] field);

    protected List<Move> getChildren(Move move, int player) {
        List<Move> list = new ArrayList<>();
        for (int i = 0; i < n * n; i++) {
            int ind = heuristic.randomArr[i];
            if (move.field[ind] == 0) {
                byte[] newBoard = Utils.cloneBoard(move.field);
                newBoard[ind] = (byte) player;
                list.add(new Move(newBoard, ind));
            }
        }
        return list;
    }

}

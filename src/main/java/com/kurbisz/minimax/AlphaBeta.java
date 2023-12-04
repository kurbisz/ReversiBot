package com.kurbisz.minimax;

import com.kurbisz.Move;
import com.kurbisz.heuristics.Heuristic;
import com.kurbisz.heuristics.MainHeuristic;

import java.util.*;

/**
 * Class with Minimax implementation containing alpha-beta pruning.
 */
public class AlphaBeta extends MinimaxAlgorithm {

    private static Object statisticsLock = new Object();
    public static long TOTAL_AM = 0, CHILDREN_AM = 0, ZERO_AM = 0, MAX_AM = 0;
    public static long[] TOTAL_AM_PARTS = {0,0,0}, CHILDREN_AM_PARTS = {0,0,0}, ZERO_AM_PARTS = {0,0,0};

    public AlphaBeta(int n, int playerNumber, int depth, Heuristic heuristic) {
        super(n, playerNumber, depth, heuristic);
    }

    /**
     * Start recursive minMax executing for choosing best move.
     * @param field board in array format
     * @return optimal move based on Minimax calculations and heuristic function
     */
    @Override
    public int getMove(byte[] field) {
        Move m = minMax(new Move(field, -1), 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true).move;
        return m.lastMove;
    }

    /**
     * Recursive implementation of Minimax algorithm with alpha-beta pruning.
     * @param m last move, by default Move instance with actual field and index -1
     * @param actDepth actual Minimax depth
     * @param alpha alpha parameter
     * @param beta beta perameter
     * @param isMax flag indicating if it is min or max player (level)
     * @return MyPair object with best move and calculated evaluation
     */
    MyPair minMax(Move m, int actDepth, int alpha, int beta, boolean isMax) {
        int endAdvantage = heuristic.getEndAdvantage(m.field);
        if (endAdvantage != 0) return new MyPair(m, endAdvantage, actDepth);

        if (actDepth == depth) {
            int evaluation = heuristic.getEvaluation(m.field);
            return new MyPair(m, evaluation);
        }
        int player = isMax ? playerNumber : 3 - playerNumber;
        Move best = null;
        int actBest = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int startAlpha = alpha;
        int startBeta = beta;
        List<Move> children = getChildren(m, player);
        int moveNr = m.getMoves();
        int stage = 0;
        for (int i = 0; i < MainHeuristic.stages.length; i++) {
            if (moveNr >= MainHeuristic.stages[i]) {
                stage = i+1;
            }
        }

        synchronized (statisticsLock) {
            TOTAL_AM_PARTS[stage]++;
            TOTAL_AM++;
            int size = children.size();
            if (size == 0) {
                ZERO_AM++;
                ZERO_AM_PARTS[stage]++;
            }
            CHILDREN_AM += size;
            CHILDREN_AM_PARTS[stage] += size;
            MAX_AM = size > MAX_AM ? size : MAX_AM;
        }

        if (children.isEmpty()) {
            return minMax(m, actDepth + 1, alpha, beta, !isMax);
        }
        for (Move move : children) {

            MyPair myPair = minMax(move, actDepth + 1, startAlpha, startBeta, !isMax);

            if (best == null || (isMax && myPair.eval > actBest) || (!isMax && myPair.eval < actBest)){
                best = move;
                actBest = myPair.eval;
            }

            if(isMax) startAlpha = Math.max(startAlpha, myPair.eval);
            else startBeta = Math.min(startBeta, myPair.eval);

            if (startBeta <= startAlpha) break;

        }
        return new MyPair(best, actBest);
    }


    class MyPair {
        Move move;
        int eval, depth;

        public MyPair(Move move, int eval) {
            this(move, eval, -1);
        }

        public MyPair(Move move, int eval, int depth) {
            this.move = move;
            this.eval = eval;
            this.depth = depth;
        }
    }

}

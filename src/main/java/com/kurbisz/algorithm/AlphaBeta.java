package com.kurbisz.algorithm;

import com.kurbisz.Move;
import com.kurbisz.heuristics.Heuristic;

import java.util.*;

public class AlphaBeta extends Algorithm {

    private static Object STATISTICS_END_TIME_OBJ = new Object(), STATISTICS_EVAL_TIME_OBJ = new Object();
    public static long STATISTICS_END_TIME = 0, STATISTICS_EVAL_TIME = 0;


    public AlphaBeta(int n, int playerNumber, int depth, Heuristic heuristic) {
        super(n, playerNumber, depth, heuristic);
    }

    @Override
    public int getMove(byte[] field) {
        Move m = minMax(new Move(field, -1), 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true).move;
        return m.lastMove;
    }

    MyPair minMax(Move m, int actDepth, int alpha, int beta, boolean isMax) {
//        long begin = System.nanoTime();
        int endAdvantage = heuristic.getEndAdvantage(m.field);
//        synchronized (STATISTICS_END_TIME_OBJ) {
//            STATISTICS_END_TIME += System.nanoTime() - begin;
//        }
        if (endAdvantage != 0) return new MyPair(m, endAdvantage, actDepth);

        if (actDepth == depth) {
//            long begin1 = System.nanoTime();
            int evaluation = heuristic.getEvaluation(m.field);
//            synchronized (STATISTICS_EVAL_TIME_OBJ) {
//                STATISTICS_EVAL_TIME += System.nanoTime() - begin1;
//            }
            return new MyPair(m, evaluation);
        }
        int player = isMax ? playerNumber : 3 - playerNumber;
        Move best = null;
        int actBest = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int startAlpha = alpha;
        int startBeta = beta;
        List<Move> children = getChildren(m, player);
        if (children.isEmpty()) {
            return minMax(m, actDepth, alpha, beta, !isMax);
        }
        for (Move move : children) {
//            if (actDepth == 0) {
//                System.out.println("TEST: " + Utils.toNormalMove(move.lastMove, n));
//            }
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

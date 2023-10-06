package com.kurbisz.algorithm;

import com.kurbisz.Move;
import com.kurbisz.Utils;
import com.kurbisz.heuristics.Heuristic;
import com.kurbisz.heuristics.HeuristicType;

import java.util.*;

public class AlphaBeta extends Algorithm {

    public static long STATISTICS_END_TIME = 0, STATISTICS_EVAL_TIME = 0;

//    public static HashMap<Long, Integer> cachedMoves = new HashMap<>();

    public AlphaBeta(int n, int playerNumber, int depth, Heuristic heuristic) {
        super(n, playerNumber, depth, heuristic);
    }

    @Override
    public int getMove(byte[] field) {
        Move m = minMax(new Move(field, -1), 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true).move;
        return m.lastMove;
    }

    MyPair minMax(Move m, int actDepth, int alpha, int beta, boolean isMax) {
        long begin = System.nanoTime();
        int endAdvantage = heuristic.getEndAdvantage(m.field);
        STATISTICS_END_TIME += System.nanoTime() - begin;
        if (endAdvantage != 0) return new MyPair(m, endAdvantage, actDepth);

        if (actDepth == depth) {
            long begin1 = System.nanoTime();
            int evaluation = heuristic.getEvaluation(m.field);
            STATISTICS_EVAL_TIME += System.nanoTime() - begin1;
            return new MyPair(m, evaluation);
        }
        int player = isMax ? playerNumber : 3 - playerNumber;
        Move best = null;
        int actBest = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int startAlpha = alpha;
        int startBeta = beta;
        for (Move move : getChildren(m, player)) {
            MyPair myPair = minMax(move, actDepth + 1, startAlpha, startBeta, !isMax);
//            if (actDepth == 0) {
//                System.out.println(move + " " + myPair.eval);
//            }
            if (best == null || (isMax && myPair.eval > actBest) || (!isMax && myPair.eval < actBest)){
                best = move;
                actBest = myPair.eval;
            }

            if(isMax) {
                startAlpha = Math.max(startAlpha, myPair.eval);
            }
            else {
                startBeta = Math.min(startBeta, myPair.eval);
            }

            if (startBeta <= startAlpha) {
                break;
            }
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

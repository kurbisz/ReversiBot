package com.kurbisz;

import com.kurbisz.heuristics.Heuristic;
import com.kurbisz.heuristics.SimpleHeuristic;

import java.io.IOException;

public class Main2 {
    public static void main(String[] args) {

        SimpleHeuristic h = new SimpleHeuristic(1, 5);
        int res = h.getEndAdvantage(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0, 2, 0, 2});
        System.out.println(res);

        /*  X____
            X____
            _X__X
            _O___
            O___O
         */
        int res2 = h.getPossibleMoves(new byte[] {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2}, 1);
        System.out.println(res2);
    }
}
package com.kurbisz.testing;

import com.kurbisz.heuristics.Heuristic;
import com.kurbisz.heuristics.SimpleHeuristic;

import java.io.IOException;

public class Testing {

    public static void main(String[] args) {
        int n = 8;

        SimpleHeuristic heuristic1 = new SimpleHeuristic(1, n);
        SimpleHeuristic heuristic2 = new SimpleHeuristic(2, n);

        int depth = 3;

        HeuristicTester heuristicTester = new HeuristicTester(heuristic1, heuristic2);
        heuristicTester.fight(depth);

    }

}

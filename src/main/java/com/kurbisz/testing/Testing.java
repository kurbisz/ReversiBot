package com.kurbisz.testing;

import com.kurbisz.heuristics.MainHeuristic;

public class Testing {

    public static void main(String[] args) {
        int n = 8;

        MainHeuristic heuristic1 = new MainHeuristic(1, n);
        MainHeuristic heuristic2 = new MainHeuristic(2, n);

        int depth = 3;

        HeuristicTester heuristicTester = new HeuristicTester(heuristic1, heuristic2);
        heuristicTester.fight(depth);

    }

}

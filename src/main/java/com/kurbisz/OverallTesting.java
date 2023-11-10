package com.kurbisz;

import com.kurbisz.algorithm.AlphaBeta;
import com.kurbisz.genetics.GeneticAlgorithm;
import com.kurbisz.player.BotPlayer;
import com.kurbisz.testing.OverallTester;


public class OverallTesting {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments!");
            return;
        }

        for (int depth = 10; depth < 20; depth++) {
            int threads;

            try {
                threads = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid thread number!");
                return;
            }

            try {
                OverallTester overallTester = new OverallTester();
                overallTester.calculate(depth, threads);
                System.out.println("DEPTH: " + depth);

            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Error with multithreading!");
            }
        }
    }

    // Coefficients: -63401 -62707 -80251 86149 44065 (9) for depth = 4 and player = 2
}
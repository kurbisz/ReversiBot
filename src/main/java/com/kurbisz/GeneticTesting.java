package com.kurbisz;

import com.kurbisz.algorithm.AlphaBeta;
import com.kurbisz.genetics.GeneticAlgorithm;
import com.kurbisz.player.BotPlayer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GeneticTesting {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Wrong number of arguments!");
            return;
        }

        int playerNumber, depth;
        try {
            playerNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid player number!");
            return;
        }
        try {
            depth = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid depth number!");
            return;
        }

        try {
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
            geneticAlgorithm.calculateForDepth(playerNumber, depth);
            System.out.println("DEPTH: " + depth);
            System.out.println("PLAYER: " + playerNumber);
            System.out.println("END TIME: " + AlphaBeta.STATISTICS_END_TIME);
            System.out.println("EVAL TIME: " + AlphaBeta.STATISTICS_EVAL_TIME);
            System.out.println("OVERALL TIME: " + BotPlayer.STATISTICS_OVERALL_TIME);

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error with multithreading!");
        }
    }

    // Coefficients: -63401 -62707 -80251 86149 44065 (9) for depth = 4 and player = 2
}
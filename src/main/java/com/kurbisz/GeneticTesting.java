package com.kurbisz;

import com.kurbisz.algorithm.AlphaBeta;
import com.kurbisz.genetics.GeneticAlgorithm;
import com.kurbisz.heuristics.SimpleHeuristic;
import com.kurbisz.player.BotPlayer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GeneticTesting {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Wrong number of arguments!");
            return;
        }

        int playerNumber, depth, threads, loops;
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
            threads = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid thread number!");
            return;
        }
        try {
            loops = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid loop number!");
            return;
        }

        try {
            System.out.println("DEPTH: " + depth);
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
            geneticAlgorithm.calculateForDepth(playerNumber, depth, threads, loops, true);
            System.out.println("TOTAL_AM: " + AlphaBeta.TOTAL_AM);
            for (int i = 0; i < 3; i++) {
                System.out.println("\tTOTAL_AM_PART: " + i + "\t" + AlphaBeta.TOTAL_AM_PARTS[i]);
            }
            System.out.println("ZERO_AM: " + AlphaBeta.ZERO_AM);
            System.out.println("ZERO_AM 2: " + (double) AlphaBeta.ZERO_AM / AlphaBeta.TOTAL_AM);
            for (int i = 0; i < 3; i++) {
                System.out.println("\tZERO_AM_PART: " + i + "\t" + AlphaBeta.ZERO_AM_PARTS[i]);
                System.out.println("\tZERO_AM_PART 2: " + i + "\t" + (double) AlphaBeta.ZERO_AM_PARTS[i] / AlphaBeta.TOTAL_AM_PARTS[i]);
            }
            System.out.println("CHILDREN_AM: " + AlphaBeta.CHILDREN_AM);
            System.out.println("CHILDREN_AM 2: " + (double) AlphaBeta.CHILDREN_AM / AlphaBeta.TOTAL_AM);
            for (int i = 0; i < 3; i++) {
                System.out.println("\tCHILDREN_AM_PART: " + i + "\t" + AlphaBeta.CHILDREN_AM_PARTS[i]);
                System.out.println("\tCHILDREN_AM_PART 2: " + i + "\t" + (double) AlphaBeta.CHILDREN_AM_PARTS[i] / AlphaBeta.TOTAL_AM_PARTS[i]);
            }
            System.out.println("MAX_AM: " + AlphaBeta.MAX_AM);
            System.out.println("GAME_AM: " + GameServer.GAME_AM);
            System.out.println("GAME_LENGTH: " + GameServer.GAME_LENGTH);
            System.out.println("GAME_LENGTH 2: " + (double) GameServer.GAME_LENGTH / GameServer.GAME_AM);
            System.out.println("MIN_GAME_LENGTH: " + GameServer.MIN_GAME_LENGTH);
            System.out.println("MAX_GAME_LENGTH: " + GameServer.MAX_GAME_LENGTH);


        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error with multithreading!");
        }
    }

}
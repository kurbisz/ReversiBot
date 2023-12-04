package com.kurbisz.parser.commands;

import com.kurbisz.GameServer;
import com.kurbisz.exceptions.ParserException;
import com.kurbisz.minimax.AlphaBeta;
import com.kurbisz.genetics.GeneticAlgorithm;

/**
 * Command responsible for executing genetic algorithm with printing results on default output.
 */
public class GeneticTesting implements CommandExecutor {

    protected int depth, threads, loops;

    /**
     * Initialize parameters for genetic algorithm.
     * @param depth depth of minimax bot
     * @param threads number of threads that can be used for games
     * @param loops number of loops in genetic algorithm
     */
    public GeneticTesting(int depth, int threads, int loops) {
        this.depth = depth;
        this.threads = threads;
        this.loops = loops;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Starting genetic algorithm with depth=" + depth
                    + ". After each loop results containing best heuristics will be printed.");
            System.out.println("Coefficients of each heuristic are grouped by game stage. Order inside of "
                    + "each group is the same as in graduation work, described also in MainHeuristic class.");
            System.out.println("At the end there is array which defines order of selecting moves with the "
                    + "same evaluation value.");
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
            geneticAlgorithm.calculateForDepth(depth, threads, loops, false);

            System.out.println("\nGame stages are named as follows: 0 - early, 1 - mid, 2 - late");

            System.out.println("Total amount of recursive minMax executions: " + AlphaBeta.TOTAL_AM);
            for (int i = 0; i < 3; i++) {
                System.out.println("\tduring stage " + i + ": " + AlphaBeta.TOTAL_AM_PARTS[i] + " ("
                        + (double) 100 * AlphaBeta.TOTAL_AM_PARTS[i] / AlphaBeta.TOTAL_AM + "%)");
            }

            System.out.println("Amount of zero possible moves: " + AlphaBeta.ZERO_AM + " ("
                    + (double) 100 * AlphaBeta.ZERO_AM / AlphaBeta.TOTAL_AM + "%)");
            for (int i = 0; i < 3; i++) {
                System.out.println("\tduring stage " + i + ": " + AlphaBeta.ZERO_AM_PARTS[i] + " ("
                        + (double) 100 * AlphaBeta.ZERO_AM_PARTS[i] / AlphaBeta.TOTAL_AM_PARTS[i] + "%)");
            }

            System.out.println("Total amount of available moves: " + AlphaBeta.CHILDREN_AM);
            System.out.println("Average amount of available moves: " + (double) AlphaBeta.CHILDREN_AM / AlphaBeta.TOTAL_AM);
            for (int i = 0; i < 3; i++) {
                System.out.println("\tduring stage " + i + ": " + (double) AlphaBeta.CHILDREN_AM_PARTS[i] / AlphaBeta.TOTAL_AM_PARTS[i]);
            }

            System.out.println("Maximum amount of available moves: " + AlphaBeta.MAX_AM);
            System.out.println("Total amount of games: " + GameServer.GAME_AM);
            System.out.println("Total amount of moves: " + GameServer.GAME_LENGTH);
            System.out.println("Average game length: " + (double) GameServer.GAME_LENGTH / GameServer.GAME_AM);
            System.out.println("Minimum game length: " + GameServer.MIN_GAME_LENGTH);
            System.out.println("Amount of games with full board filled (60 moves): " + GameServer.MAX_GAME_LENGTH_AM + " ("
                        + (double) 100 * GameServer.MAX_GAME_LENGTH_AM / GameServer.GAME_AM + "%)");


        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error with multithreading!");
        }


    }

}
package com.kurbisz.utils;

import com.kurbisz.genetics.HeuristicData;
import com.kurbisz.heuristics.MainHeuristic;

import java.util.Random;

/**
 * Static class for generating objects connected with heuristic function.
 */
public final class MainHeuristicUtils {

    protected static int n = 8, fromCoefficient = -100000, toCoefficient = 100000;
    private static Random r = new Random();

    /**
     * Generate new HeuristicData instance with random coefficients.
     * @param playerNumber number of player for HeuristicData
     * @return new random instance of HeuristicData
     */
    public static HeuristicData getRandom(int playerNumber) {
        int coeff[][] = new int[MainHeuristic.stages.length+1][MainHeuristic.coefficientAmount];
        for (int stage = 0; stage <= MainHeuristic.stages.length; stage++) {
            for (int i = 0; i < MainHeuristic.coefficientAmount; i++) {
                coeff[stage][i] = getRandomNumber();
            }
        }

        int[] randomArr = Utils.getRandomPermutation(n);
        return new HeuristicData(new MainHeuristic(playerNumber, n, coeff, randomArr));
    }

    /**
     * Get random coefficient value from predefined range.
     * @return random coefficient value
     */
    private static int getRandomNumber() {
        return r.nextInt(toCoefficient - fromCoefficient) + fromCoefficient;
    }

}

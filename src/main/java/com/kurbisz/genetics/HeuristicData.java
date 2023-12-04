package com.kurbisz.genetics;

import com.kurbisz.heuristics.MainHeuristic;

import java.util.Arrays;

/**
 * Class storing information about heuristic and its game results.
 */
public class HeuristicData implements Comparable<HeuristicData> {
    public MainHeuristic heuristic;
    public Integer wins = 0, rounds = 0;
    public Integer id = -1;

    public HeuristicData(MainHeuristic heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Compare two instances by its number of wins.
     * @param heuristicData other HeuristicData instance to compare
     * @return the value 0 if number of wins are equal, 1 if this
     *              instance has more wins and -1 otherwise
     */
    @Override
    public int compareTo(HeuristicData heuristicData) {
        return heuristicData.wins.compareTo(wins);
    }

    /**
     * Convert HeuristicData to string.
     * @return string representation of HeuristicData instance
     */
    @Override
    public String toString() {
        String str = "";

        for (int stage = 0; stage <= MainHeuristic.stages.length; stage++) {
            for (int i = 0; i < MainHeuristic.coefficientAmount; i++) {
                str += heuristic.coefficients[stage][i] + ", ";
            }
            str += "\t\t";
        }
        str += " (" + rounds + "," + wins + ") - " + Arrays.toString(heuristic.randomArr);
        return str;
    }

    /**
     * Get new instance of HeuristicData by given string (in same format as it is done in `toString`).
     * @param playerNumber number of player for new instance
     * @param n size of board (default 8)
     * @param str string holding information about new HeuristicData
     * @return new object of HeuristicData created from `str`
     */
    public static HeuristicData fromString(int playerNumber, int n, String str) {
        String[] result = str.split("\t\t");
        int[][] coefficients = new int[MainHeuristic.stages.length+1][MainHeuristic.coefficientAmount];
        for (int stage = 0; stage <= MainHeuristic.stages.length; stage++) {
            String s = result[stage];
            String[] coeffs = s.split(", ");
            coefficients[stage] = Arrays.stream(coeffs).mapToInt(Integer::parseInt).toArray();
        }

        String rest = result[MainHeuristic.stages.length+1];
        String[] splitted = rest.split(" - ");
        String[] randoms = splitted[1].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
        int[] random = Arrays.stream(randoms).mapToInt(Integer::parseInt).toArray();

        String[] values = splitted[0].replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)", "").split(",");
        int[] value = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();

        MainHeuristic mainHeuristic = new MainHeuristic(playerNumber, n, coefficients, random);
        HeuristicData heuristicData = new HeuristicData(mainHeuristic);
        heuristicData.rounds = value[0];
        heuristicData.wins = value[1];
        return heuristicData;
    }

}
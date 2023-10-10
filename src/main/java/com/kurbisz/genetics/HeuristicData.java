package com.kurbisz.genetics;

import com.kurbisz.heuristics.SimpleHeuristic;

import java.util.Arrays;

public class HeuristicData implements Comparable<HeuristicData> {
    public SimpleHeuristic heuristic;
    public Integer wins = 0, rounds = 0;

    public HeuristicData(SimpleHeuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public int compareTo(HeuristicData o) {
        return o.wins.compareTo(wins);
    }

    @Override
    public String toString() {
        String str = "";

        for (int stage = 0; stage <= SimpleHeuristic.stages.length; stage++) {
            for (int i = 0; i < SimpleHeuristic.coefficientAmount; i++) {
                str += heuristic.coefficients[stage][i] + ", ";
            }
            str += "\t\t";
        }
        str += " (" + rounds + "," + wins + ") - " + Arrays.toString(heuristic.randomArr);
        return str;
    }

    public static HeuristicData fromString(int playerNumber, int n, String str) {
        String[] result = str.split("\t\t");
        int[][] coefficients = new int[SimpleHeuristic.stages.length+1][SimpleHeuristic.coefficientAmount];
        for (int stage = 0; stage <= SimpleHeuristic.stages.length; stage++) {
            String s = result[stage];
            String[] coeffs = s.split(", ");
            coefficients[stage] = Arrays.stream(coeffs).mapToInt(Integer::parseInt).toArray();
        }

        String rest = result[SimpleHeuristic.stages.length+1];
        String[] splitted = rest.split(" - ");
        String[] randoms = splitted[1].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
        int[] random = Arrays.stream(randoms).mapToInt(Integer::parseInt).toArray();

        String[] values = splitted[0].replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)", "").split(",");
        int[] value = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();

        SimpleHeuristic simpleHeuristic = new SimpleHeuristic(playerNumber, n, coefficients, random);
        HeuristicData heuristicData = new HeuristicData(simpleHeuristic);
        heuristicData.rounds = value[0];
        heuristicData.wins = value[1];
        return heuristicData;
    }

}
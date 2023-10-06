package com.kurbisz.testing;

import com.kurbisz.heuristics.Heuristic;
import com.kurbisz.heuristics.SimpleHeuristic;

import java.io.IOException;

public class Testing {

    public static void main(String[] args) {
        int n = 5;
//        Heuristic heuristic1 = new SimpleHeuristic(1, n,
//                new int[] {-40000, 40000, 50000, -50000, 30000, -30000, -10000, 70000, 100000, -100000, -60000, 60000},
//                new int[] {23, 19, 10, 5, 7, 24, 0, 18, 13, 15, 11, 20, 14, 21, 12, 6, 4, 22, 9, 2, 3, 16, 8, 1, 17});
//        Heuristic heuristic2 = new SimpleHeuristic(1, n,
//                new int[] {-8388, 27245, 64661, -33667, -20573, -8522, 16596, -90011, 60769, -24973, -3906, 51561},
//                new int[] {0, 4, 20, 24, 1, 3, 5, 9, 15, 19, 21, 23, 2, 6, 8, 10, 14, 16, 18, 22, 7, 11, 13, 17, 12});
//        Heuristic heuristic2 = new SimpleHeuristic(1, n,
//                new int[] {-4264, 29648, 66657, -34212, -17800, -14303, 16607, -58422, 78960, -52347, -10962, 53968},
//                new int[] {23, 6, 0, 7, 12, 4, 10, 5, 14, 19, 2, 16, 11, 15, 13, 1, 24, 8, 17, 20, 21, 22, 18, 3, 9});
        Heuristic heuristic1 = new SimpleHeuristic(1, n,
                new int[][]{
                        new int[]{-7578, 25117, 78294, -497543, 95827, 34870, -1327, -45492, 4251, -536, 21613, -105619},
                        new int[]{-7578, 25117, 78294, -497543, 95827, 34870, -1327, -45492, 4251, -536, 21613, -105619},
                        new int[]{-7578, 25117, 78294, -497543, 95827, 34870, -1327, -45492, 4251, -536, 21613, -105619},
                },
                new int[] {23, 6, 0, 7, 12, 4, 10, 5, 14, 19, 2, 16, 11, 15, 13, 1, 24, 8, 17, 20, 21, 22, 18, 3, 9},
                new int[] {-2865, 6, -37, 2350, 657});
        Heuristic heuristic2 = new SimpleHeuristic(1, n,
                new int[][]{
                        new int[]{-13238, 6625, 134419, -631398, 155550, 13938, -438, -275793, 2540, -253, 2756, -53487},
                        new int[]{-13238, 6625, 134419, -631398, 155550, 13938, -438, -275793, 2540, -253, 2756, -53487},
                        new int[]{-13238, 6625, 134419, -631398, 155550, 13938, -438, -275793, 2540, -253, 2756, -53487},
                },
                new int[] {15, 17, 8, 3, 22, 18, 6, 16, 4, 0, 2, 19, 13, 14, 7, 9, 20, 1, 24, 11, 5, 23, 12, 10, 21},
                new int[] {-9176, 0, 0, 1901, 477});
//        Heuristic heuristic2 = new OutsideHeuristic(1, n);
//        Heuristic heuristic2 = new SimpleHeuristic(1, n,
//                new int[] {33553, 64520, 58052, 69544, -37070, -47844, 78161, 153082, -66933, -35139, 83058, 32112},
////                new int[] {22, 15, 9, 17, 11, 23, 10, 2, 1, 7, 5, 3, 19, 21, 24, 0, 14, 6, 8, 16, 13, 4, 12, 20, 18});
//        Heuristic heuristic2 = new SimpleHeuristic(1, n,
//                new int[] {-38534, 42543, -42141, 73156, -108430, 13880, 2174, 38977, -102594, 97208, 22611, -8581},
//                new int[] {0, 4, 20, 24, 1, 3, 5, 9, 15, 19, 21, 23, 2, 6, 8, 10, 14, 16, 18, 22, 7, 11, 13, 17, 12});
//        Heuristic heuristic2 = new SimpleHeuristic(1, n,
//                new int[] {-114152, 1440, -12160, -47614, -84412, -69883, -109517, 48897, -12244, 106602, -4750, -88221},
//                new int[] {14, 19, 0, 17, 7, 24, 6, 18, 8, 15, 11, 4, 23, 10, 12, 21, 20, 22, 9, 2, 5, 16, 13, 1, 3});

        if (args.length != 4) {
            System.out.println("Wrong number of arguments!");
            return;
        }

        String host = args[0];
        int port, playerNumber, depth;
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port!");
            return;
        }
        try {
            playerNumber = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid player number!");
            return;
        }
        try {
            depth = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid depth number!");
            return;
        }

        try {
            HeuristicTester heuristicTester = new HeuristicTester(heuristic1, heuristic2);
            heuristicTester.fight(host, port, depth);
        } catch (IOException e) {
            System.out.println("Error with connecting to server!");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error with multithreading!");
        }

    }

}

package com.kurbisz.genetics;

import com.kurbisz.GameServer;
import com.kurbisz.heuristics.Heuristic;
import com.kurbisz.heuristics.SimpleHeuristic;
import com.kurbisz.Utils;
import com.kurbisz.player.BotPlayer;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GeneticAlgorithm {

    private String folderName = "results", lastFileName = "last-loop.txt";
    private static int n = 8, fromCoefficient = -100000, toCoefficient = 100000;
    private int amount = 16, left = 5, crossover = 4, randoms = 3;
    //    private int amount = 32, left = 12, crossover = 8, randoms = 4;
//    private int amount = 64, left = 24, crossover = 12, randoms = 16;
    private static Random r = new Random();

    public GeneticAlgorithm() {}

    public GeneticAlgorithm(int n, int amount, int left, int randoms, int fromCoefficient, int toCoefficient) {
        this.n = n;
        this.amount = amount;
        this.left = left;
        this.randoms = randoms;
        this.fromCoefficient = fromCoefficient;
        this.toCoefficient = toCoefficient;
    }

    public void calculateForDepth(int playerNumber, int depth, int threads, int loops, boolean loadProgress) throws InterruptedException {
        List<HeuristicData> list = new ArrayList<>();

        createResultsFolder();

        int startLoop = 0;
        if (loadProgress) startLoop = readFromLastFile(playerNumber, list);

        int act = 0;
        while (list.size() < amount) {
            HeuristicData rand = getRandom(playerNumber);
            rand.id = act;
            list.add(rand);
            act++;
        }

        for (int nr = 0; nr < loops; nr++) {
            for (HeuristicData heuristicData : list) {
                heuristicData.wins = 0;
                heuristicData.rounds++;
            }

            final ExecutorService exec = Executors.newFixedThreadPool(threads);

            Object sync = new Object();
            for (int i = 1; i < amount; i++) {
                for (int j = 0; j < amount; j++) {
                    HeuristicData heuristicData1 = list.get(j);
                    heuristicData1.heuristic.playerNumber = playerNumber;
                    Heuristic heuristic1 = heuristicData1.heuristic.clone();

                    HeuristicData heuristicData2 = list.get((j + i) % amount);
                    heuristicData2.heuristic.playerNumber = 3 - playerNumber;
                    Heuristic heuristic2 = heuristicData2.heuristic.clone();

//                    int res = gameServer.play();
//                    if (res == playerNumber) heuristicData1.wins++;
//                    else if (res == 3 - playerNumber) heuristicData2.wins++;
                    final int ii = j, jj = (j + i) % amount;
                    Runnable runnable = () -> {
                        BotPlayer player1 = new BotPlayer(playerNumber, depth, heuristic1, false);

                        BotPlayer player2 = new BotPlayer(3 - playerNumber, depth, heuristic2, false);

                        final GameServer gameServer = new GameServer(player1, player2);
//                        System.out.println("STARTED " + ii + " vs " + jj);
                        int res = gameServer.play();
//                        System.out.println("ENDED " + ii + " vs " + jj + ": " + res);
                        synchronized (sync) {
                            if (res == playerNumber) heuristicData1.wins++;
                            else if (res == 3 - playerNumber) heuristicData2.wins++;
                        }
                    };
                    exec.submit(runnable);
                }
            }

            exec.shutdown();
            exec.awaitTermination(1, TimeUnit.DAYS);

            try {
                PrintWriter writer = new PrintWriter(folderName + File.separator + "loop-" + (nr + startLoop + 1) + ".txt", "UTF-8");
                PrintWriter lastWriter = new PrintWriter(folderName + File.separator + lastFileName, "UTF-8");
                lastWriter.println((nr + startLoop + 1));
                for (HeuristicData heuristic : list) {
                    String str = heuristic.toString();
                    writer.println(str);
                    lastWriter.println(str);
                }
                writer.close();
                lastWriter.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            list = getBest(list);

            System.out.println("");
            System.out.println("FINISHED LOOP " + (nr+startLoop));
            for (HeuristicData heuristic : list) {
                System.out.println(heuristic);
            }


            while (list.size() < crossover) {
                list.add(getCrossover(list));
            }

            while (list.size() < amount - randoms) {
                list.add(getMutated(list));
            }

            while (list.size() < amount) {
                list.add(getRandom(playerNumber));
            }

        }


    }

    private void createResultsFolder() {
        File directory = new File(folderName);
        if (! directory.exists()){
            directory.mkdir();
        }
    }

    private int readFromLastFile(int playerNumber, List<HeuristicData> list) {
        File file = new File(folderName + File.separator + lastFileName);

        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String firstLine;
                int nr = 0;
                if ((firstLine = reader.readLine()) != null) {
                    nr = Integer.parseInt(firstLine);
                }

                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(HeuristicData.fromString(playerNumber, n, line));
                }
                reader.close();
                System.out.println("LOADED PROGRESS FROM FILE " + lastFileName + "!");
                return nr;
            } catch (IOException e) {}
        }
        return 0;
    }

    public static HeuristicData getRandom(int playerNumber) {
        int coeff[][] = new int[SimpleHeuristic.stages.length+1][SimpleHeuristic.coefficientAmount];
        for (int stage = 0; stage <= SimpleHeuristic.stages.length; stage++) {
            for (int i = 0; i < SimpleHeuristic.coefficientAmount; i++) {
                coeff[stage][i] = getRandomNumber();
            }
        }

        int[] randomArr = Utils.getRandomPermutation(n);
        return new HeuristicData(new SimpleHeuristic(playerNumber, n, coeff, randomArr));
    }

    private HeuristicData getMutated(List<HeuristicData> list) {
        SimpleHeuristic heuristic1 = list.get(r.nextInt(list.size())).heuristic;
        double diff = 0.05;
        double changeValChance = 0.1;

        int coeff[][] = new int[SimpleHeuristic.stages.length + 1][SimpleHeuristic.coefficientAmount];
        for (int stage = 0; stage <= SimpleHeuristic.stages.length; stage++) {
            for (int i = 0; i < SimpleHeuristic.coefficientAmount; i++) {
                coeff[stage][i] = getRandomCoefficient(heuristic1.coefficients[stage][i], diff);
                if (r.nextDouble() < changeValChance) {
                    coeff[stage][i] = getRandomNumber();
                }
            }
        }

        return new HeuristicData(new SimpleHeuristic(heuristic1.playerNumber, n, coeff, Utils.getSimilarPermutation(heuristic1.randomArr, 2)));
    }

    private HeuristicData getCrossover(List<HeuristicData> list) {
        SimpleHeuristic heuristic1 = list.get(r.nextInt(list.size())).heuristic;
        SimpleHeuristic heuristic2 = list.get(r.nextInt(list.size())).heuristic;

        int coeff[][] = new int[SimpleHeuristic.stages.length+1][SimpleHeuristic.coefficientAmount];
        for (int stage = 0; stage <= SimpleHeuristic.stages.length; stage++) {
            for (int i = 0; i < SimpleHeuristic.coefficientAmount; i++) {
                if (r.nextDouble() < 0.5) coeff[stage][i] = heuristic1.coefficients[stage][i];
                else coeff[stage][i] = heuristic2.coefficients[stage][i];
            }
        }

        return new HeuristicData(new SimpleHeuristic(heuristic1.playerNumber, n, coeff, Utils.getSimilarPermutation(heuristic1.randomArr, 2)));
    }



    private List<HeuristicData> getBest(List<HeuristicData> list) {
        Collections.sort(list);
        return list.stream().limit(left).collect(Collectors.toList());
    }

    private static int getRandomNumber() {
        return r.nextInt(toCoefficient - fromCoefficient) + fromCoefficient;
    }

    private double getRandomMultiplier(double diff) {
        return (1.0-diff) + r.nextDouble()*2*diff;
    }

    private int getRandomCoefficient(int coefficient, double diff) {
        int res = (int) (coefficient * getRandomMultiplier(diff)) + (int) (toCoefficient * getRandomMultiplier(0.5)/100 * ((r.nextBoolean()) ? -1 : 1));
        res = Math.max(res, 10*fromCoefficient);
        res = Math.min(res, 10*toCoefficient);
        return res;
    }

    private int[] getRandomPlaceCoefficients() {
        int placeCoefficient[] = new int[n];
        for (int i = 0; i < n; i++) {
            placeCoefficient[i] = getRandomNumber() / 10;
        }
        return placeCoefficient;
    }


}

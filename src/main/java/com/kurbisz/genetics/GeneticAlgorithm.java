package com.kurbisz.genetics;

import com.kurbisz.GameServer;
import com.kurbisz.heuristics.Heuristic;
import com.kurbisz.heuristics.MainHeuristic;
import com.kurbisz.utils.Utils;
import com.kurbisz.player.BotPlayer;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Class responsible for running genetic algorithm.
 */
public class GeneticAlgorithm {


    protected String folderName = "results", lastFileName = "last-loop.txt";
    protected int n = 8, fromCoefficient = -100000, toCoefficient = 100000,
            amount = 64, left = 24, crossover = 12, randoms = 16;
    private Random r = new Random();


    public GeneticAlgorithm() {}

    public GeneticAlgorithm(int amount, int left, int randoms, int fromCoefficient, int toCoefficient) {
        this.amount = amount;
        this.left = left;
        this.randoms = randoms;
        this.fromCoefficient = fromCoefficient;
        this.toCoefficient = toCoefficient;
    }

    /**
     * Run genetic algorithm for given parameters.
     * @param depth depth of Minimax algorithm
     * @param threads number of threads to use in genetic algorithm
     * @param loops number of total loops
     * @param loadProgress flag indicating if progress should be loaded and saved to output
     * @throws InterruptedException when error during multithreading appears
     */
    public void calculateForDepth(int depth, int threads, int loops, boolean loadProgress) throws InterruptedException {
        int playerNumber = 1;
        List<HeuristicData> list = new ArrayList<>();

        createResultsFolder();

        int startLoop = 0;
        if (loadProgress) startLoop = readFromLastFile(list);

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

                    Runnable runnable = () -> {
                        BotPlayer player1 = new BotPlayer(playerNumber, depth, heuristic1, false);

                        BotPlayer player2 = new BotPlayer(3 - playerNumber, depth, heuristic2, false);

                        final GameServer gameServer = new GameServer(player1, player2);
                        int res = gameServer.play();
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

            if (loadProgress) {
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
            }

            list = getBest(list);

            System.out.println("");
            System.out.println("FINISHED LOOP " + (nr+startLoop+1));
            int i = 0;
            for (HeuristicData heuristic : list) {
                i++;
                System.out.println("Coefficients of heuristic number " + i + ": " + heuristic);
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

    /**
     * Generate new HeuristicData with random coefficients.
     * @param playerNumber number of player for MainHeuristic
     * @return random instance of HeuristicData
     */
    protected HeuristicData getRandom(int playerNumber) {
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
     * Get new HeuristicData mutated from random instance inside the list.
     * @param list list of HeuristicData to choose for mutation
     * @return new mutated HeuristicData
     */
    protected HeuristicData getMutated(List<HeuristicData> list) {
        MainHeuristic heuristic1 = list.get(r.nextInt(list.size())).heuristic;
        double diff = 0.05;
        double changeValChance = 0.1;

        int coeff[][] = new int[MainHeuristic.stages.length + 1][MainHeuristic.coefficientAmount];
        for (int stage = 0; stage <= MainHeuristic.stages.length; stage++) {
            for (int i = 0; i < MainHeuristic.coefficientAmount; i++) {
                coeff[stage][i] = getRandomCoefficient(heuristic1.coefficients[stage][i], diff);
                if (r.nextDouble() < changeValChance) {
                    coeff[stage][i] = getRandomNumber();
                }
            }
        }

        return new HeuristicData(new MainHeuristic(heuristic1.playerNumber, n, coeff, Utils.getSimilarPermutation(heuristic1.randomArr, 2)));
    }

    /**
     * Get new HeuristicData created by crossover from instances inside the list.
     * @param list list of HeuristicData to choose for crossover
     * @return new HeuristicData created by doing crossover
     */
    protected HeuristicData getCrossover(List<HeuristicData> list) {
        MainHeuristic heuristic1 = list.get(r.nextInt(list.size())).heuristic;
        MainHeuristic heuristic2 = list.get(r.nextInt(list.size())).heuristic;

        int coeff[][] = new int[MainHeuristic.stages.length+1][MainHeuristic.coefficientAmount];
        for (int stage = 0; stage <= MainHeuristic.stages.length; stage++) {
            for (int i = 0; i < MainHeuristic.coefficientAmount; i++) {
                if (r.nextDouble() < 0.5) coeff[stage][i] = heuristic1.coefficients[stage][i];
                else coeff[stage][i] = heuristic2.coefficients[stage][i];
            }
        }

        return new HeuristicData(new MainHeuristic(heuristic1.playerNumber, n, coeff, Utils.getSimilarPermutation(heuristic1.randomArr, 2)));
    }


    /**
     * Reduce given list to list of best instances.
     * @param list list of all HeuristicData instances
     * @return new list with best HeuristicData instances
     */
    protected List<HeuristicData> getBest(List<HeuristicData> list) {
        Collections.sort(list);
        return list.stream().limit(left).collect(Collectors.toList());
    }

    /**
     * Get random coefficient from defined range.
     * @return random coeeficient
     */
    protected int getRandomNumber() {
        return r.nextInt(toCoefficient - fromCoefficient) + fromCoefficient;
    }

    /**
     * Get random multiplier for getRandomCoefficient operation from range (1-diff, 1+diff).
     * @param diff parameter defining size of range
     * @return random number from given range
     */
    protected double getRandomMultiplier(double diff) {
        return (1.0-diff) + r.nextDouble()*2*diff;
    }

    /**
     * Get random coefficient for mutation process and adjust it if needed.
     * @param coefficient actual coefficient
     * @param diff defining size of range
     * @return new coefficient value
     */
    protected int getRandomCoefficient(int coefficient, double diff) {
        int res = (int) (coefficient * getRandomMultiplier(diff)) + (int) (toCoefficient * getRandomMultiplier(0.5)/100 * ((r.nextBoolean()) ? -1 : 1));
        res = Math.max(res, 10*fromCoefficient);
        res = Math.min(res, 10*toCoefficient);
        return res;
    }

    /**
     * Create folder for results if it does not exist.
     */
    private void createResultsFolder() {
        File directory = new File(folderName);
        if (! directory.exists()){
            directory.mkdir();
        }
    }

    /**
     * Read last iteration from given file and add its HeuristicData instances to provided list.
     * @param list list to add read instances
     * @return number of last (read from file) iteration
     */
    private int readFromLastFile(List<HeuristicData> list) {
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
                    list.add(HeuristicData.fromString(1, n, line));
                }
                reader.close();
                System.out.println("LOADED PROGRESS FROM FILE " + lastFileName + "!");
                return nr;
            } catch (IOException e) {}
        }
        return 0;
    }

}

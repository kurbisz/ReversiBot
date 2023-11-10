package com.kurbisz.testing;

import com.kurbisz.GameServer;
import com.kurbisz.genetics.HeuristicData;
import com.kurbisz.heuristics.Heuristic;
import com.kurbisz.player.BotPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OverallTester {

    private String folderName = "overallResults", inputFileName = "input.txt";
    private static int n = 8;

    public void calculate(int depth, int threads) throws InterruptedException {
        List<HeuristicData> list = new ArrayList<>();
        createResultsFolder();

        readFromInputFile(list);

        for (HeuristicData heuristicData : list) {
            heuristicData.wins = 0;
            heuristicData.rounds++;
        }

        final ExecutorService exec = Executors.newFixedThreadPool(threads);

        int playerNumber = 1;

        Object sync = new Object();
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                HeuristicData heuristicData1 = list.get(j);
                heuristicData1.heuristic.playerNumber = playerNumber;
                Heuristic heuristic1 = heuristicData1.heuristic.clone();

                HeuristicData heuristicData2 = list.get((j + i) % list.size());
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
        exec.awaitTermination(100, TimeUnit.DAYS);

        try {
            PrintWriter outWriter = new PrintWriter(folderName + File.separator + "output-" + depth + ".txt", "UTF-8");
            for (int i = 0; i < list.size(); i++) {
                outWriter.print(list.get(i).wins + " ");
            }
            outWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private void createResultsFolder() {
        File directory = new File(folderName);
        if (!directory.exists()){
            directory.mkdir();
        }
    }

    private void readFromInputFile(List<HeuristicData> list) {
        File file = new File(folderName + File.separator + inputFileName);

        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(HeuristicData.fromString(1, n, line));
                }
                reader.close();
                System.out.println("LOADED INPUT FROM FILE " + inputFileName + "!");
            } catch (IOException e) {}
        }
    }

}

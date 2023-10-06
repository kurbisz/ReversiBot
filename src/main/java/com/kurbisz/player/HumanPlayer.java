package com.kurbisz.player;

import com.kurbisz.Board;
import com.kurbisz.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanPlayer extends Player {

    BufferedReader reader;

    public HumanPlayer(int playerNumber) {
        super(playerNumber);
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public int play(Board b) {
        while (true) {
            try {
                b.printBoard();
                if (!isAnyMove(b.fields)) {
                    System.out.println("You do not have any possible moves! Your turn was skipped.");
                    return -1;
                }
                System.out.print("Enter your move (" + (playerNumber == 1 ? "X" : "O") + "): ");
                String number = reader.readLine();
                Integer fieldNr = Integer.parseInt(number);
                int field = Utils.toIndexMove(fieldNr, n);

                if (field >= 0 && field < n * n && Utils.isValidMove(b.fields, n, playerNumber, field)) {
                    return field;
                }
            } catch (IOException | NumberFormatException e) {}

            System.out.println("Invalid field number!");
        }
    }

    public boolean isAnyMove(byte[] field) {
        for (int i = 0; i < n * n; i++) {
            if (Utils.isValidMove(field, n, playerNumber, i)) {
                return true;
            }
        }
        return false;
    }

}

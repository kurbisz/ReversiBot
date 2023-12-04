package com.kurbisz.player;

import com.kurbisz.Board;
import com.kurbisz.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Implementation for human player.
 */
public class HumanPlayer extends Player {

    BufferedReader reader;

    /**
     * Initialize number of player and reader for input.
     * @param playerNumber
     */
    public HumanPlayer(int playerNumber) {
        super(playerNumber);
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Print board and wait for player to input his move. Validate it and
     * repeat if it was invalid, otherwise convert format of move and return it.
     * 
     * @param b actual Board instance
     * @return move that has been done by player
     */
    @Override
    public int play(Board b) {
        while (true) {
            try {
                b.printBoard();
                if (!Utils.isAnyMove(b.fields, n, playerNumber)) {
                    System.out.println("You do not have any possible moves!");
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

            System.out.println("Invalid field number!\n");
        }
    }

}

package com.kurbisz;

import com.kurbisz.player.Player;

public class GameServer {

    private static boolean logs = true;

    // players[0] - white, players[1] - black
    private Player[] players;

    private int n = 8;



    public GameServer(Player player1, Player player2) {
        this.players = new Player[] {player1, player2};
    }

    public int play() {
        Board board = getStartingBoard();
        byte actualPlayer = 0;
        int lastMove = -1;
        while (true) {
            int move = players[actualPlayer].play(board);
            if (move < 0) {
                if (lastMove < 0) break;
                continue;
            }
            if (logs) System.out.println("Player " + (actualPlayer == 1 ? "X" : "O") + " moved to " + Utils.toNormalMove(move, n));
            board.move(move, (byte) (actualPlayer + 1));
            actualPlayer = (byte) (1 - actualPlayer);
        }
        int winner = Utils.getWinner(board.fields, n);
        if (logs) {
            if (winner == 2) System.out.println("DRAW");
            else System.out.println("PLAYER " + (actualPlayer == 1 ? "X" : "O") + " WON!");
        }
        return winner;
    }

    private Board getStartingBoard() {
        Board board = new Board();
        board.changeField(3, 4, (byte) 1);
        board.changeField(4, 3, (byte) 1);
        board.changeField(3, 3, (byte) 2);
        board.changeField(4, 4, (byte) 2);
        return board;
    }

}

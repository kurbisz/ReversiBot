package com.kurbisz;

import com.kurbisz.player.Player;
import com.kurbisz.utils.Utils;

/**
 * Class responsible for running game with given players.
 */
public class GameServer {

    // variables for gathering statistics
    private static Object statisticsLock = new Object();
    public static long GAME_AM = 0, GAME_LENGTH = 0, MIN_GAME_LENGTH = 64, MAX_GAME_LENGTH_AM = 0;

    private boolean logs = false;
    private int n = 8;

    // players[0] - X, players[1] - O
    protected Player[] players;


    public GameServer(Player player1, Player player2) {
        this.players = new Player[] {player1, player2};
    }

    /**
     * Execute game between players and return its result.
     * If 'logs' flag is set then print basic information about gameplay.
     *
     * @return number of player that won, 0 means draw
     */
    public int play() {
        Board board = getStartingBoard();
        byte actualPlayer = 0;
        int lastMove = -1;
        while (true) {
            int move = players[actualPlayer].play(board);
            if (move < 0) {
                if (lastMove < 0) break;
                lastMove = -1;
                continue;
            }
            if (logs) System.out.println("Player " + (actualPlayer == 0 ? "X" : "O") + " moved to " + Utils.toNormalMove(move, n));
            board.move(move, (byte) (actualPlayer + 1));
            actualPlayer = (byte) (1 - actualPlayer);
            lastMove = move;
        }
        int moves = board.getMoves();
        synchronized (statisticsLock) {
            GAME_AM++;
            GAME_LENGTH += moves;
            if (moves == 60) MAX_GAME_LENGTH_AM++;
            MIN_GAME_LENGTH = moves < MIN_GAME_LENGTH ? moves : MIN_GAME_LENGTH;
        }
        int winner = Utils.getWinner(board.fields, n);
        if (logs) {
            if (winner == 2) System.out.println("DRAW");
            else System.out.println("PLAYER " + (actualPlayer == 0 ? "X" : "O") + " WON!");
        }
        if (winner == 2) return 0;
        return players[winner].getPlayerNumber();
    }

    /**
     * Create new object with board and initialize it with default pawns.
     * @return board with starting game state
     */
    private Board getStartingBoard() {
        Board board = new Board();
        board.changeField(3, 4, (byte) 1);
        board.changeField(4, 3, (byte) 1);
        board.changeField(3, 3, (byte) 2);
        board.changeField(4, 4, (byte) 2);
        return board;
    }

}

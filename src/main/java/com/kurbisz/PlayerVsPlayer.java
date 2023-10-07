package com.kurbisz;

import com.kurbisz.player.HumanPlayer;

public class PlayerVsPlayer {
    public static void main(String[] args) {
        HumanPlayer player1 = new HumanPlayer(1);
        HumanPlayer player2 = new HumanPlayer(2);
        GameServer gameServer = new GameServer(player1, player2);
        gameServer.play();
    }
}
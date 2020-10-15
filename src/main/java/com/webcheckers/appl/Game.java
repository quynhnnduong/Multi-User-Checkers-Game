package com.webcheckers.appl;

import com.webcheckers.model.Player;

public class Game {
    private Player redPlayer;
    private Player whitePlayer;
    private int id;

    public Game(Player redPlayer, Player whitePlayer, int id){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.id = id;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public int getId(){
        return id;
    }
}

package com.webcheckers.model;

import java.util.ArrayList;

/**
 * Stores a game as a replay with its id and turns
 */
public class Replay {

    private Game game;
    private ArrayList<Turn> turns;
    private String gameId;

    public Replay(Game game){
        this.game = game;
        this.gameId = game.getId();
        this.turns = new ArrayList<>();
    }

    /**
     * save each turn
     */
    public void saveTurn(){
        turns.add(game.getCurrentTurn());
    }

    public String getGameId() {
        return gameId;
    }
}

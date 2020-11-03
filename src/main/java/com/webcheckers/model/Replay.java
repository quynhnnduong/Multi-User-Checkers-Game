package com.webcheckers.model;

import java.util.ArrayList;

/**
 * Stores a game as a replay with its id and turns
 */
public class Replay {


    private ArrayList<String> turnsList;
    private String gameId;

    public Replay(String gameId, ArrayList<String> turnsList){
        this.gameId = gameId;
        this.turnsList = new ArrayList<>();
    }

    /**
     * save each turn
     */
    //public void saveTurn(){
    //    turns.add(game.getCurrentTurn());
    //}

    public String getGameId() {
        return gameId;
    }

    public ArrayList<String> getTurnsList() {
        return turnsList;
    }

    public String getTurn(int i){
        return turnsList.get(i);
    }
}

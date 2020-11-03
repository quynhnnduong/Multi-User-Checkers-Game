package com.webcheckers.model;

import java.util.ArrayList;

/**
 * Stores a game as a replay with its id and turns
 */
public class Replay {


    private ArrayList<String> turnsList;
    private String gameId;
    private int currentTurn;
    private int maxTurns;
    private Game game;
    private Player redPlayer;
    private Player whitePlayer;

    public Replay(String gameId, ArrayList<String> turnsList, Game game){
        this.gameId = gameId;
        this.turnsList = turnsList;
        maxTurns = turnsList.size() - 1;
        this.game = game;
        this.redPlayer = game.getRedPlayer();
        this.whitePlayer = game.getWhitePlayer();
        currentTurn = 0;

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

    public void incrementTurn(){
        if (currentTurn < turnsList.size() - 1) {
            currentTurn += 1;
        }
    }

    public void decrementTurn(){
        if (currentTurn > 0) {
            currentTurn -= 1;
        }
    }

    public String getCurrentTurn(){
        return turnsList.get(currentTurn);
    }

    public Player getRed(){
        return redPlayer;
    }

    public Player getWhite(){
        return whitePlayer;
    }

    public Game getGame(){
        return game;
    }
}

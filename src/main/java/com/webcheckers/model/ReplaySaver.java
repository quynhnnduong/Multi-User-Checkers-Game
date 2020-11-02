package com.webcheckers.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that saves games as a collection of replays once the game ends
 */
public class ReplaySaver {

    private final HashMap<String, Game> replays = new HashMap<>();
    private final ArrayList<String> savedGames = new ArrayList<>();

    public void addReplay(Game game){
        replays.put(game.getId(), game);
    }

    /**
     * Converts all turns in a game into a string to be read by the replay class
     * TODO call this when the game ends
     * @param gameId
     * @return
     */
    public String convertGameToText(String gameId){
        Game game = replays.get(gameId);
        String gameTurns = game.turnsToString();
        System.out.println(gameTurns);
        savedGames.add(gameTurns);
        return gameTurns;

        //for (String id : replays.keySet()){
        //    Game game = replays.get(id);
        //}
    }

    /**
     * save the turn of the current replay with the specified gameId
     * @param gameId
     * @param replay
     */
    public void saveTurn(String gameId, Replay replay){
        //replays.get(gameId).saveTurn();
    }

    public ArrayList<String> getSavedGames() {
        return savedGames;
    }
}

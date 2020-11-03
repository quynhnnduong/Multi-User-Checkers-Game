package com.webcheckers.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that saves games as a collection of replays once the game ends
 */
public class ReplaySaver {

    private final HashMap<String, Replay> replays = new HashMap<>();
    //private final ArrayList<String> savedGames = new ArrayList<>();


    public void saveReplay(Game game){
        ArrayList<String> replayText = convertGameToList(game);
        replays.put(game.getId(), new Replay(game.getId(), replayText, game));

    }

    /**
     * Converts all turns in a game into a string to be read by the replay class
     * TODO call this when the game ends
     * @param
     * @return
     */
    public ArrayList<String> convertGameToList(Game game){
        return game.turnsToList();

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

    public Replay getReplay(String gameId) {
        return replays.get(gameId);
    }

    public HashMap<String, Replay> getAllReplays(){
        return replays;
    }


}

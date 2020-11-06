package com.webcheckers.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that saves games as a collection of replays once the game ends
 * @author Joel Clyne
 */
public class ReplaySaver {

    private final HashMap<String, Replay> replays = new HashMap<>();

    /**
     * Saves a game as replay whenever someone wins a game
     * @param game the game to save
     */
    public void saveReplay(Game game){
        ArrayList<ReplayMove> replayText = convertGameToList(game);
        replays.put(game.getId(), new Replay(game.getId(), replayText, game));

    }

    /**
     * Converts all turns in a game into a list of replayMoves to be read by the replay class when someone watches a replay
     * @param game the game to record
     * @return the game as a list of replayMoves
     */
    public ArrayList<ReplayMove> convertGameToList(Game game){
        return game.turnsToList();
    }

    /**
     * gets the replay of the specified gameId
     * @param gameId the gameID of the desired replay object
     * @return a replay object with the gameID
     */
    public Replay getReplay(String gameId) {
        return replays.get(gameId).makeCopy();
    }

    /**
     * gets all the saved replays
     * @return the saved replays
     */
    public HashMap<String, Replay> getAllReplays(){
        return replays;
    }


}

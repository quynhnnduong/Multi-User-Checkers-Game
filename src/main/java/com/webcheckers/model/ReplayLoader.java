package com.webcheckers.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages Loading Replays for use with the replay mode view
 * @author Joel Clyne
 */
public class ReplayLoader {

    //The class that manages the saving of all the replays
    private final ReplaySaver replaySaver;

    //The current turn of the replay being displayed
    private int turnNumber;

    //The maximum amount of turns
    private final int turnMax;


    public ReplayLoader(ReplaySaver replaySaver){
            this.replaySaver = replaySaver;
            turnNumber = 0;
            turnMax = replaySaver.getAllReplays().size() - 1;

    }

    /**Increments the currently loaded replay's turn by 1
     * @return true if successful, false on fail
     */
    public boolean incrementTurn(){
        if (turnNumber != turnMax) {
            turnNumber += 1;
            return true;
        }
        return false;
    }

    /**Decrements the currently loaded replay's turn by 1
     * @return true if successful, false on fail
     */
    public boolean decrementTurn(){
        if (turnNumber != 0) {
            turnNumber -= 1;
            return true;
        }
        return false;
    }

    /**
     * gets the replay of the specified gameId
     * @param gameId the gameID of the desired replay object
     * @return a replay object with the gameID
     */
    public Replay getReplay(String gameId){
        return replaySaver.getReplay(gameId);
    }

    /**
     * Gets all the saved replays
     * @return all the saved replays
     */
    public ArrayList<Replay> getAllReplays(){
        return new ArrayList<>(replaySaver.getAllReplays().values());
    }






}



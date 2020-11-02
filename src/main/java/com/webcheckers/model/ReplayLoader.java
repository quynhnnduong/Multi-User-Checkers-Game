package com.webcheckers.model;

import java.util.ArrayList;

public class ReplayLoader {

    private ArrayList<String> replayList;
    private int turnNumber;
    private int turnMax;

    public ReplayLoader(ReplaySaver replaySaver){
            this.replayList = replaySaver.getSavedGames();
            turnNumber = 0;
            turnMax = replaySaver.getSavedGames().size() - 1;
    }

    /**
     * returns true if successful, false on fail
     * @return
     */
    public boolean incrementTurn(){
        if (turnNumber != turnMax) {
            turnNumber += 1;
            return true;
        }
        return false;
    }

    /**
     * returns true if successful, false on fail
     * @return
     */
    public boolean decrementTurn(){
        if (turnNumber != 0) {
            turnNumber -= 1;
            return true;
        }
        return false;
    }

    public String getTurn(){
        return replayList.get(turnNumber);
    }






}



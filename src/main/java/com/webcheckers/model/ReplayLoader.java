package com.webcheckers.model;

import java.util.ArrayList;
import java.util.HashMap;

public class ReplayLoader {

    private HashMap<String, String> replayList;
    private ReplaySaver replaySaver;
    private int turnNumber;
    private int turnMax;


    public ReplayLoader(ReplaySaver replaySaver){
            this.replaySaver = replaySaver;
            this.replayList = new HashMap<>();
            turnNumber = 0;
            turnMax = replaySaver.getAllReplays().size() - 1;
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

    public String getTurn(String gameId, int i){
        return replaySaver.getReplay(gameId).getTurn(i);
    }


    public void printReplayById(String gameId){
        String replayText = replayList.get(gameId);
        for (Replay replay : replaySaver.getAllReplays().values()){
            if (replay.getGameId().equals(gameId)){
                for (String turn : replay.getTurnsList()){
                    System.out.println(turn);
                }
            }
        }
    }

    public Replay getReplay(String gameId){
        return replaySaver.getReplay(gameId);
    }

    public ArrayList<Replay> getAllReplays(){
        return new ArrayList<>(replaySaver.getAllReplays().values());
    }






}



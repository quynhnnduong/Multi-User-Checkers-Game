package com.webcheckers.model;

import java.util.ArrayList;

/**
 * A representation of the domain object, 'Checkers Game'. This object stores information
 * regarding each turn that a Player takes. The turns are stored in an ArrayList structure.
 *
 * @author Dmitry selin
 */
public class Game {

    /** A structure containing a chronological list of turns that a Player took during the game */
    private final ArrayList<Turn> turns;

    /** A gameID for an instance of Game (redPlayer's Name + whitePlayer's Name) */
    private final String id;

    /**
     * Creates a new instance of Game - used in GameCenter to keep track of all existing Games.
     *
     * @param id the ID of the Game
     */
    public Game(String id){
        this.id = id;
        turns = new ArrayList<>();
        addTurn();
    }

    /** Adds a new Turn to turns - used when it is the Player's turn */
    public void addTurn() { turns.add(new Turn()); }

    /**
     * Returns the last Turn object in turns. This represents the Turn that the Player
     * is currently taking.
     *
     * @return the Turn that the Player is currently on (still modifying)
     */
    public Turn getCurrentTurn() { return turns.get(turns.size() - 1); }

    /**
     * Simply returns the Game's ID.
     *
     * @return the ID of the Game
     */
    public String getId(){ return id; }
}

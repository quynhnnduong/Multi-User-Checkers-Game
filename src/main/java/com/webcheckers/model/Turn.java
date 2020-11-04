package com.webcheckers.model;

import java.util.ArrayList;

/**
 * This class represents the Turn domain object and is used for holding all the
 * necessary information regarding what took place on a particular Player's Turn.
 *
 * @author Dmitry Selin
 */
public class Turn {

    /** An ArrayList of all the validated Moves that took place (or currently taking place) on a Player's Turn */
     final ArrayList<Move> validMoves = new ArrayList<>();

    /**
     * Simply appends a validated Move to the end of validMoves.
     *
     * @param move a valid Move object
     */
    public void addValidMove(Move move) { validMoves.add(move); }

    /** If the validMoves structure is not empty, then remove the last Move (used for PostBackupMove) */
    public void removeLastValidMove() {
        if (hasMoves())
            validMoves.remove(validMoves.size() - 1);
    }

    /**
     * Returns if any Moves have been validated this Turn
     *
     * @return true if some Moves have been added to validMoves, otherwise false
     */
    public boolean hasMoves() { return !validMoves.isEmpty(); }

    /**
     * This method returns the last Move that was validated on this Turn.
     *
     * @return the last validated Move if it exists, otherwise null
     */
    public Move getLastMove() {

        if (hasMoves())
            return validMoves.get(validMoves.size() - 1);

        return null;
    }

    /**
     * Simply returns the ArrayList of all the Moves made on this Turn.
     *
     * @return the list of Moves made on this turn
     */
    public ArrayList<Move> getAllMoves(){ return validMoves; }
}

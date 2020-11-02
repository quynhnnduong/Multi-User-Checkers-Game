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
    private final ArrayList<Move> validMoves = new ArrayList<>();

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
     * This method returns the first Move that was validated on this Turn.
     *
     * @return the first validated Move if it exists, otherwise null
     */
    public Move getFirstMove() {
        if (hasMoves())
            return validMoves.get(0);

        return null;
    }

    public Move getLastMove() {
        if (hasMoves()) {
            return validMoves.get(validMoves.size() - 1);
        }
        return null;
    }

    public Position getLastStartPosition(){
        return getLastMove().getStart();
    }

    public Position getLastEndPosition() {
        return getLastMove().getEnd();
    }

    public ArrayList<Move> getAllMovesInTurn(){
        return validMoves;
    }

    @Override
    public String toString() {
        StringBuilder fullTurnsString = new StringBuilder();
        for (Move move: validMoves){
            fullTurnsString.append(move.toString());
            fullTurnsString.append("-\n");
        }
        return fullTurnsString.toString();
    }
}

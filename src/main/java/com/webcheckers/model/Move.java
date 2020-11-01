package com.webcheckers.model;

import java.util.ArrayList;

/**
 * A representation of the domain object, Move. This object is used when interacting with
 * individual checkers Pieces on the BoardView. A Move simply contains the starting and ending
 * Position that constitutes a single checkers Move (from one Position to a new Position).
 *
 * @author Dmitry Selin
 */
public class Move {

    /** The starting Position for the Move */
    private final Position start;

    /** The ending Position for the Move */
    private final Position end;

    /**
     * Creates a new Move object.
     *
     * @param start the starting Position
     * @param end the ending Position
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Simply returns the starting Position of the Move.
     *
     * @return the starting Position
     */
    public Position getStart() { return start; }

    /**
     * Returns the ending Position of the Move.
     *
     * @return the ending Position
     */
    public Position getEnd() { return end; }

    /**
     * This method is used for updating the BoardView of the Player who did not make the Move. Since
     * the BoardView of the opponent is flipped 180 degrees, the coordinates of the starting and ending
     * Positions of the flipped Move would be the BOARD_SIZE minus each current coordinate. This flipped Move
     * would correspond to the Move made by the current Player on the opponent's BoardView.
     *
     * @return a flipped version of the current Move (for updating the opponent's BoardView)
     */
    public Move getFlippedMove() {

        // Coordinates are subtracted from BOARD_SIZE to "flip" the individual Positions
        Position newStart = new Position(((BoardView.BOARD_SIZE - 1) - start.getRow()),
                ((BoardView.BOARD_SIZE - 1) - start.getCell()));

        Position newEnd = new Position(((BoardView.BOARD_SIZE - 1) - end.getRow()),
                ((BoardView.BOARD_SIZE - 1) - end.getCell()));

        return new Move(newStart, newEnd);
    }

    /**
     * @return the difference in columns between the start and end positions of the move
     */
    public int getColDiff(){
        return getStart().getRow() - getEnd().getRow();
    }

    /**
     *
     * @return the difference in rows between the start and end positions of the move
     */
    public int getRowDiff(){
        return Math.abs(getEnd().getCell() - getStart().getCell());
    }
}

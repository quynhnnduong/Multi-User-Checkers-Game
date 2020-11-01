package com.webcheckers.model;

/**
 * A Pure Fabrication that serves to assist with classes such as Move
 * and BoardView. This class represents as single Position on the checkers board with
 * row and cell being the coordinates of the Position.
 *
 * @author Dmitry Selin
 */
public class Position {

    /** The Row index at which the Position is denoted at */
    private final int row;

    /** The index of the Space within the Row at which the Position is denoted at */
    private final int cell;

    /**
     * Creates a new Position object.
     *
     * @param row the index of a Row of the checkers board
     * @param cell the index of the specific Space on the Row of the checkers board
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Returns the index of the Space/cell of the Row.
     *
     * @return the index of the Space within the Row of Position
     */
    public int getCell() { return cell; }

    /**
     * Simply returns the index of the Row of the Position object
     *
     * @return the index of the Row
     */
    public int getRow() { return row; }
}

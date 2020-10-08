package com.webcheckers.model;

/**
 * This class represents the "Space" domain object. This object is housed in a
 * Row object and contains a potential "Piece" object that represents an occupying
 * checkers Piece.
 *
 * @author Shubhang Mehrotra, Joel Clyne, Dmitry Selin
 */
public class Space {

    /** The index of the Space within a Row of the checkers board */
    private final int cellIdx;

    /** The color of the Space tile */
    private final SpaceColor color;

    /** The Piece object occupying the Space */
    private Piece piece = null;

    /**
     * Creates a new space on the checkers board.
     *
     * @param cellIdx index of the Space within a row
     * @param color the color of the space
     */
    public Space(int cellIdx, SpaceColor color){
        this.cellIdx = cellIdx;
        this.color = color;
    }

    /**
     * Simply returns the index of the Space within a row (0-7)
     *
     * @return the index of the Space within a row
     */
    public int getCellIdx() { return cellIdx; }

    /**
     * Places a Piece on this Space.
     *
     * @param piece the Piece that is to occupy this Space
     */
    public void placePiece(Piece piece) { this.piece = piece; }

    /**
     * This method simply returns a boolean that corresponds to if this Space is valid for a Piece
     * to occupy it, meaning it meets the criteria...
     *
     * 1. The Space is colored black
     *
     * 2. The Space has nno other piece occupying it
     *
     * @return if the space is valid for a piece to occupy it
     */
    public boolean isValid(){ return color == SpaceColor.BLACK && piece == null; }

    /**
     * Returns the Piece object that is occupying the Space - simply returns
     * null if there is no Piece occupying the Space.
     *
     * @return the Piece occupying the Space or null
     */
    public Piece getPiece(){ return piece; }

    /**
     * Simply returns the color of the Space.
     *
     * @return returns the enumerated color of the Space tile
     */
    public SpaceColor getColor() { return color; }
}

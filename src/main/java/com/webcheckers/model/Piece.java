package com.webcheckers.model;

/**
 * This class represents the domain object, checkers Pieces. A Piece has
 * certain attributes (type, color) and occupies a Space on the
 * checkers board (BoardView).
 *
 * @author Shubhang Mehrotra, Joel Clyne, Dmitry Selin
 */
public class Piece {

    /** An enumeration that distinguishes between the two types of checkers Pieces: King and Single */
    enum Type{
        SINGLE,
        KING
    }

    /** An enumeration of the only two colors that a checkers Piece can be: Black and Red */
    public enum Color {
        RED,
        WHITE
    }

    /** The color of the checkers Piece */
    private final Color color;

    /** The type of checkers Piece this object is */
    private Type type;

    /**
     * Creates a new checkers Piece. Every Piece always starts
     * as a SINGLE.
     *
     * @param color the color of the checkers Piece
     */
    public Piece (Color color){
        type = Type.SINGLE;
        this.color = color;
    }

    /**
     * Returns the color of the checkers Piece
     *
     * @return an enumeration of the color of the checkers Piece
     */
    public Color getColor(){ return color; }

    /**
     * Returns the checkers Piece's type (KING or SINGLE)
     *
     * @return an enumeration of the checkers Piece's type
     */
    public Type getType(){ return type; }
}

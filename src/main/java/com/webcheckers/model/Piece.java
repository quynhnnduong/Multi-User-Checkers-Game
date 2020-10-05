package com.webcheckers.model;

/**
 * Model object to represent a piece
 * @author Shubhang Mehrotra
 *  Added returning Enum and constructor
 * @author Joel Clyne
 */
public class Piece {

    private enum type{
        SINGLE,
        KING
    }

    private enum color{
        RED,
        WHITE
    }

    private type rank;
    private color clr;


    /**
     * Constructor for a piece they all start as a single
     * @param clr the color of the piece
     */
    public Piece (color clr){
        this.rank = type.SINGLE;
        this.clr = clr;
    }

    /**
     Returns the pieces's color
     * @return the enum of the piece's color
     * @author Joel Clyne
     */
    public color getColor(){
        return clr;
    }

    /**
     * Returns the pieces's type
     * @return the enum of the piece's type
     * @author Joel Clyne
     */
    public type getType(){
        return rank;
    }
}

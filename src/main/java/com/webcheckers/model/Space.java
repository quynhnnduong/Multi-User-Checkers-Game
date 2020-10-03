package com.webcheckers.model;

/**
 * Model object to represent a Space in the Rows on the boards
 * @author Shubhang Mehrotra
 */
public class Space {
    private int cellIdx;

    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * method to check the validity of the space
     * TODO: Reuires game logic to be written to check the validity of a space.
     * @return boolean
     */
    public boolean isValid(){
        return false;
    }

    /**
     * TODO: Implement Piece
     * @return
     */
    public Piece getPiece(){
        return new Piece();
    }
}

package com.webcheckers.model;

/**
 * Model object to represent a Space in the row on the boards
 * @author Shubhang Mehrotra
 */
public class Space {
    private int cellIdx;
    private Piece piece;

    /**
     * Constructor for creating a space on the board
     * @param cellIdx index of cell
     * @param piece the piece on current space
     */
    public Space(int cellIdx, Piece piece){
        this.cellIdx = cellIdx;
        this.piece = piece;

    }
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * method to check the validity of the space
     * TODO: Reuires game logic to be written to check the validity of a space.
     * @return boolean
     */
    public boolean isValid(){
        return true;
    }

    /**
     * TODO: Implement Piece
     * @return
     */
    public Piece getPiece(){
        return piece;
    }
}

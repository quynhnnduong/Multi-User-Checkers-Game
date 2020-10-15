package com.webcheckers.model;

/**
 * Denotes the position of a move, used by Move class
 */
public class Position {
    private int row;
    private int cell;

    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    public int getCell() {
        return cell;
    }

    public int getRow() {
        return row;
    }
}

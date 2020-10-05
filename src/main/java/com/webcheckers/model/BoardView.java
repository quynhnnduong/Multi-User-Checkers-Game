package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Model class to view the 8 rows that comprise the board
 * @author Joel Clyne
 */

public class BoardView implements Iterable<Row>{

    static final int BOARD_SIZE = 8;

    //An iterable list of rows
    private ArrayList<Row> rows;

    /**
     * Constructor setting up board
     * Doesn't need a param bcs there's always 8 rows
     * generateEightRowBoard fills in the rows
     */
    public BoardView() {
        this.rows = generateEightRowBoard();

    }



    /**
     * Returns an iterator for the rows
     * @return the iterator for the rows
     */
    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    /**
     * Generate an arraylist of 8 rows to create a "board"
     * @return an arraylist of 8 rows
     */
    public ArrayList<Row> generateEightRowBoard(){
        ArrayList<Row> rows = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < BOARD_SIZE; rowIdx++){
            rows.add(new Row(rowIdx));
        }
        return rows;
    }


}

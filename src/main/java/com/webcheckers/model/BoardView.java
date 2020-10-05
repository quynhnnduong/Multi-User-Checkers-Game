package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView implements Iterable<Row>{

    static final int BOARD_SIZE = 8;
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
     * @author Joel Clyne
     */
    @Override
    public Iterator iterator() {
        return rows.iterator();
    }

    /**
     * Generate an arraylist of 8 rows to create a "board"
     */
    public ArrayList<Row> generateEightRowBoard(){
        ArrayList<Row> rows = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < 8; rowIdx++){
            rows.add(new Row(rowIdx));
        }
        return rows;
    }


}

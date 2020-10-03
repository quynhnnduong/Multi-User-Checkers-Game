package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * !!!!TEMPORARY!!!! TODO: Figure out what to do....
 * Model class to represent the Board
 * @author Shubhang Mehrotra, Quynh Duong
 */
public class BoardView implements Iterable{

    //Create a board object for current player
    private Board board;
    static final int BOARD_SIZE = 8;


    /**
     * Constructor setting up board
     * @param board the board
     */
    public BoardView(Board board) {
        this.board = board;
    }

    public Board getBoard(){
        return board;
    }
    /**
     * Adds rows to board.
     * TODO (Quynh): Still not sure if this is correct so feel free to change it later
     * @return iterator
     * @author Quynh Duong
     */
    @Override
    public Iterator<Row> iterator(){
        List<Row> rows = new ArrayList<>();
        for(int i = 0; i < BOARD_SIZE; i++)
            rows.add(new Row(i, board));
        return rows.iterator();
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }
}

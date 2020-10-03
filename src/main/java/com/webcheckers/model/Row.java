package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Model class for a Row
 * @author Shubhang Mehrotra
 */
public class Row implements Iterable {

    private int index;
    private Board board;

    /**
     * Sets row in board
     * @param index index of row
     * @param board board
     */
    public Row(int index, Board board){
        this.index = index;
        this.board = board;
    }

    /**
     * Add spaces to each row
     * @return iterator of spaces
     * @author Quynh Duong
     */
    @Override
    public Iterator<Space> iterator() {
        List<Space> spaces = new ArrayList<>();
        for(int i = 0; i < BoardView.BOARD_SIZE; i++){
            spaces.add(board.getSpaceIndex(index,i));
        }
        return spaces.iterator();
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }

    public int getIndex() {
        return index;
    }
}

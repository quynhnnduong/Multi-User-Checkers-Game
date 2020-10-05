package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Model class for a Row
 * @author Joel Clyne
 */
public class Row implements Iterable<Space> {

    private int index;
    private ArrayList<Space> spaces;

    /**
     * Sets row in board
     * @param index index of row
     */
    public Row(int index){
        this.index = index;
        this.spaces = generateEightSpaceRow();
    }

    /**
     * gets iterator for the row's spaces
     * @return iterator of spaces
     * @author Joel Clyne
     */
    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }


    public int getIndex() {
        return index;
    }

    /**
     * Generate an arraylist of 8 spaces to create a "row"
     */
    public ArrayList<Space> generateEightSpaceRow(){
        ArrayList<Space> spaces = new ArrayList<>();
        for (int cellIdx = 0; cellIdx < 8; cellIdx++){
            spaces.add(new Space(cellIdx));
        }
        return spaces;
    }
}

package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Model class for a Row
 * @author Joel Clyne
 */
public class Row implements Iterable<Space> {

    private int index;
    //the list of spaces in the row
    private ArrayList<Space> spaces;

    /**
     * creates a row with an index and an arrayList of spaces
     * needs no space parameter because generateEightSpaceRow makes it
     * @param index index of row
     */
    public Row(int index){
        this.index = index;
        this.spaces = generateEightSpaceRow();
    }

    /**
     * gets iterator for the row's spaces
     * @return iterator of spaces
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
     * @return an arrayList of 8 spaces
     */
    public ArrayList<Space> generateEightSpaceRow(){
        ArrayList<Space> spaces = new ArrayList<>();
        for (int cellIdx = 0; cellIdx < 8; cellIdx++){
            spaces.add(new Space(cellIdx));
        }
        return spaces;
    }
}

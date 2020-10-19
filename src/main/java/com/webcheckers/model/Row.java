package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents the model component and domain object, Row. A Row
 * contains eight spaces and represents one row of the checkers board.
 *
 * @author Joel Clyne, Dmitry Selin
 */
public class Row implements Iterable<Space> {

    /** The index of the Row within the checkers board (0-7) */
    private final int index;

    /** The ArrayList that houses the eight spaces that represent a Row */
    private ArrayList<Space> row;

    /**
     * Creates a new Row object with a specified index within a checkers
     * board and a chosen leading Space color (color of the first Space tile in the row).
     *
     * @param index index of row within a checkers board
     * @param leadingColor the first color of a Space in the Row
     */
    public Row(int index, SpaceColor leadingColor){
        this.index = index;
        row = generateRow(leadingColor);
    }

    private Row(int newIndex, ArrayList<Space> spaces) {
        index = newIndex;
        row = spaces;
    }

    /**
     * Returns the iterator of the Row object.
     *
     * @return iterator generated by row
     */
    @Override
    public Iterator<Space> iterator() {
        return row.iterator();
    }

    /**
     * Simply returns the index of the Row object in context to the checkers board.
     *
     * @return the index of the Row within a checkers board (0-7)
     */
    public int getIndex() { return index; }

    /**
     * A helper method that creates all the Space objects and places them inside
     * the row ArrayList (as specified by leadingColor). Additionally, this method also
     * places red and white pieces on the valid spaces of the bottom and top 3 rows
     * (the index determines the location of the row).
     *
     * @param leadingColor the color of the first Space tile in the row
     *
     * @return an ArrayList of eight Space objects with alternating colors
     */
    public ArrayList<Space> generateRow(SpaceColor leadingColor) {
        ArrayList<Space> row = new ArrayList<>();

        // Creates all Space objects and adds them to row
        for (int i = 0; i < BoardView.BOARD_SIZE; i++){
            Space newSpace = new Space(i, leadingColor);

            // Places the checkers Pieces at the top and bottom 3 Rows (RED -> Bottom, WHITE -> Top)
            if (index <= 2 && newSpace.isValid())
                newSpace.placePiece(new Piece(Piece.Color.WHITE));
            else if (index >= 5 && newSpace.isValid())
                newSpace.placePiece(new Piece(Piece.Color.RED));

            row.add(i, newSpace);

            // Swaps colors for each alternating Space to create a checker pattern
            if (leadingColor == SpaceColor.BLACK)
                leadingColor = SpaceColor.WHITE;
            else
                leadingColor = SpaceColor.BLACK;
        }

        return row;
    }

    public Row flipRow(int newIndex) {
        ArrayList<Space> flippedRow = new ArrayList<>();
        int index = 0;

        for (int i = (BoardView.BOARD_SIZE - 1); i >= 0; i--) {
            Space newSpace = new Space(index, row.get(i).getColor());
            newSpace.placePiece(row.get(i).getPiece());

            flippedRow.add(newSpace);
        }

        return new Row(newIndex, flippedRow);
    }

    public ArrayList<Space> getSpaces(){ return row; }
}

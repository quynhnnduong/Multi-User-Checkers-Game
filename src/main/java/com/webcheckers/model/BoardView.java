package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A representation of the domain object, Board. The BoardView class contains an
 * ArrayList data structure that contains all the Rows that make up a checkers
 * board.
 *
 * @author Joel Clyne, Dmitry Selin
 */

public class BoardView implements Iterable<Row>{

    /** The size of the checkers board (square) */
    static final int BOARD_SIZE = 8;

    /** An ArrayList containing the Row objects that make up the checkers board */
    private ArrayList<Row> board;

    /** A simple constructor that creates a clean and set checkers board. */
    public BoardView() { board = generateBoard(); }

    /**
     * This constructor is used for duplicating the BoardView class when
     * flipping the board (requires an existing ArrayList or Rows aka board).
     *
     * @param board an ArrayList or Rows that represent a checkers board
     */
    public BoardView(ArrayList<Row> board) { this.board = board; }

    /**
     * Returns an iterator representation of the board.
     *
     * @return an iterator generated from board
     */
    @Override
    public Iterator<Row> iterator() {
        return board.iterator();
    }

    /**
     * Generates a brand new checkers board with preset Pieces. The board will follow a
     * "checkered pattern" or black and white Spaces with the top left corner always being
     * white. Red Pieces will always be placed at the bottom 3 Rows of the board, while black Pieces
     * will be placed at the top 3 Rows.
     *
     * @return an ArrayList or Rows representing a clean and set checkers board
     */
    public ArrayList<Row> generateBoard(){
        ArrayList<Row> rows = new ArrayList<>();

        // The top left corner of an 8 x 8 checkers board is always white
        // This means that the first Space of the first Row must be white
        SpaceColor leadingColor = SpaceColor.WHITE;

        // Creates BOARD_SIZE number of Rows
        for (int i = 0; i < BOARD_SIZE; i++){
            rows.add(new Row(i, leadingColor));

            // Alternates the leading color in each Row to create a checker pattern
            if (leadingColor == SpaceColor.BLACK)
                leadingColor = SpaceColor.WHITE;
            else
                leadingColor = SpaceColor.BLACK;
        }

        return rows;
    }

    /**
     * Returns a copy of this BoardView object, only with a flipped board.
     * This is is used to give the white player a BoardView with the white
     * Pieces at the bottom.
     *
     * @return a copy of this BoardView object, except with a flipped board
     */
    public BoardView getFlippedBoard() { return new BoardView(this.flipBoard()); }

    /**
     * A helper function used for flipping the Rows of the board ArrayList.
     * A flipped board simply has its first Row as its new last Row (this is
     * done to give each Player a BoardView from their color's perspective).
     *
     * @return a flipped version of board
     */
    private ArrayList<Row> flipBoard() {
        ArrayList<Row> flippedBoard = new ArrayList<>();

        // Reverses the order of the Rows without changing the Pieces
        for (int i = 7; i >= 0; i--) {
            flippedBoard.add(board.get(i));
        }

        return flippedBoard;
    }

    public ArrayList<Row> getRows(){
        return board;
    }
}

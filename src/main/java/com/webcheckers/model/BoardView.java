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
    private final ArrayList<Row> board;

    /** A simple constructor that creates a clean and set checkers board. */
    public BoardView(Piece.Color bottomColor) { board = generateBoard(bottomColor); }

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
    public ArrayList<Row> generateBoard(Piece.Color bottomColor){
        ArrayList<Row> rows = new ArrayList<>();

        // The top left corner of an 8 x 8 checkers board is always white
        // This means that the first Space of the first Row must be white
        SpaceColor leadingColor = SpaceColor.WHITE;

        // Creates BOARD_SIZE number of Rows
        for (int i = 0; i < BOARD_SIZE; i++){
            rows.add(new Row(i, leadingColor, bottomColor));

            // Alternates the leading color in each Row to create a checker pattern
            if (leadingColor == SpaceColor.BLACK)
                leadingColor = SpaceColor.WHITE;
            else
                leadingColor = SpaceColor.BLACK;
        }

        return rows;
    }

    public ArrayList<Row> getBoard(){
        return board;
    }

    public void makeMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();

        Space startSpace = board.get(start.getRow()).getSpace(start.getCell());
        Space endSpace = board.get(end.getRow()).getSpace(end.getCell());

        Piece piece = startSpace.getPiece();
        startSpace.removePiece();

        endSpace.placePiece(piece);
    }
}

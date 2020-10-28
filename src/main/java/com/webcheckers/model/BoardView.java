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

    /**
     * Creates a new BoardView object with the bottom checkers Pierces of color bottomColor.
     *
     * @param bottomColor the color of the checkers Pieces at the bottom of the board
     */
    public BoardView(Piece.Color bottomColor) { board = generateBoard(bottomColor); }

    /**
     * Returns an iterator representation of the board.
     *
     * @return an iterator generated from board
     */
    @Override
    public Iterator<Row> iterator() { return board.iterator(); }

    /**
     * Generates a brand new checkers board with preset Pieces. The board will follow a
     * "checkered pattern" or black and white Spaces with the top left corner always being
     * white. The color of the pieces at the bottom 3 Rows is determined by the variable, bottomColor.
     * Naturally, the top 3 Rows will use the color not in use by bottomColor. Since there are only two
     * possible colors (RED and WHITE), this deduction is relatively straightforward.
     *
     * @param bottomColor the color of the checkers Piece at the bottom 3 Rows of the board
     *
     * @return an ArrayList or Rows representing a clean and set checkers board
     */
    public ArrayList<Row> generateBoard(Piece.Color bottomColor) {
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

    /**
     * Simply returns the ArrayList of Rows representing the checkers board.
     *
     * @return the board ArrayList object (ArrayList of Rows)
     */
    public ArrayList<Row> getBoard(){ return board; }

    /**
     * This method updates the BoardView when a new Move is made. This is done
     * by moving the Piece at the starting Position of Move to the ending
     * Position of Move. The Piece is simply taken off its Space and placed on the
     * ending Position.
     *
     * @param move the Move that updates the BoardView
     */
    public void makeMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();

        // Creates and initializes the individual board Spaces that correspond to their respective Positions
        Space startSpace = board.get(start.getRow()).getSpace(start.getCell());
        Space endSpace = board.get(end.getRow()).getSpace(end.getCell());

        //check if its a jump
        //we know if its a jump if the row and cell changed by 2 spaces
        if ((Math.abs(start.getCell() - end.getCell()) == 2) && Math.abs(start.getRow() - end.getRow()) == 2){
            //remove the captured piece
            int capturedCell = (start.getCell() + end.getCell()) / 2;
            int capturedRow = (start.getRow() + end.getRow()) / 2 ;
            Space jumpedOverSpace = board.get(capturedRow).getSpace(capturedCell);
            jumpedOverSpace.removePiece();
        }

        // Gets the Piece in focus
        Piece piece = startSpace.getPiece();

        // Removes the Piece from the starting Space and places is on the ending Space
        startSpace.removePiece();
        endSpace.placePiece(piece);
    }
}

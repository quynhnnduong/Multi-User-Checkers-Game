package com.webcheckers.model;

import spark.Route;

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

    /**
     * Add capturing pieces
     * @param move
     */
    public void makeMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();

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

        Piece piece = startSpace.getPiece();
        startSpace.removePiece();

        endSpace.placePiece(piece);
    }

    /**
     * Iterates through all spaces on the player of the color params board looking for any opportunites to jump
     * @param color - the color of the player who's board is being iterated through
     * @return true if opportunity is found, false if not
     */
    public boolean checkForJumpAcrossBoard(Game.ActiveColor color){
        //TODO check backwards when we implement piece

        //check if forward left and right spaces are free for 2 spaces ahead for every space on the board

        boolean startChecking = true;

        for(Row row : board){
            for (Space space : row){
                int rowIdx = row.getIndex();
                int cellIdx = space.getCellIdx();

                //check if the space has piece
                if (space.getPiece() == null){
                    //dont bother checking
                    startChecking = false;
                //check if the pieces belong to the current player
                } else if (space.getPiece().getColor() == Piece.Color.RED && color == Game.ActiveColor.WHITE){
                    startChecking = false;
                } else if (space.getPiece().getColor() == Piece.Color.WHITE && color == Game.ActiveColor.RED){
                    startChecking = false;
                }
                //now, the only spaces left have pieces you own
                if (startChecking){
                    //System.out.println("Row = " + rowIdx + " Cell = " + cellIdx);
                    boolean possibleMoveFound = checkForJumpOnPosition(rowIdx, cellIdx, color);
                    if (possibleMoveFound){
                        return true;
                    }
                }
                startChecking = true;
            }
        }
        //if none of the spaces have the option for a move
        return false;
    }

    /**
     * checks a specific space on the board to see if it is possible for a piece on that space to do a jump
     * @param row the row of the cell
     * @param cell the column of the cell
     * @param color the color of the player who's board is being looked at
     * @return true if opportunity is found, false if not
     */
    boolean checkForJumpOnPosition(int row, int cell, Game.ActiveColor color){
        boolean forwardLeftExists = false;
        Position forwardLeft1 = null;
        Position forwardLeft2 = null;
        boolean forwardRightExists = false;
        Position forwardRight1 = null;
        Position forwardRight2 = null;

        boolean jumpAvailable;

        //check if there even are spaces to go through
        if (row < BOARD_SIZE - 2 && cell > 2 ) {
            forwardLeft1 = new Position(row - 1, cell - 1);
            forwardLeft2 = new Position(row - 2, cell - 2);
            forwardLeftExists  = true;
        }

        if (row < BOARD_SIZE - 2 && cell < BOARD_SIZE - 2) {
            forwardRight1 = new Position(row - 1, cell + 1);
            forwardRight2 = new Position(row - 2, cell + 2);
            forwardRightExists = true;
        }

        //if (row == 4 && cell == 3){
        //    System.out.println(forwardLeftExists + " - left, " + forwardRightExists + " - right");
        //    System.out.println("wrong move found below");
        //}

        //if the spaces are able to reached for any pair, we have to check if there is a piece there
        if (forwardLeftExists) {
            jumpAvailable = pieceThenEmptySpaceExists(forwardLeft1, forwardLeft2, color);
            if (jumpAvailable) {
                //System.out.println("found left");
                return true;
            }
        }
        if (forwardRightExists) {
            jumpAvailable = pieceThenEmptySpaceExists(forwardRight1, forwardRight2, color);
            if (jumpAvailable) {
                //System.out.println("found right");
                return true;
            }
        }
        return false;
    }

    /**
     * checks if a space has a immediately diagonal neighbor of an opponents piece, and then if the space
     * diagonal to the opponent's piece space is free (think of what a possible jump looks like in checkers)
     * @param oneSpaceAhead the immediately diagonal neighbor of the origin space
     * @param twoSpacesAhead the immediately diagonal neighbor of one space ahead
     * @param playerColor the color of the player's view
     * @return true if the jump opportunity is found, false if not
     */
    boolean pieceThenEmptySpaceExists(Position oneSpaceAhead, Position twoSpacesAhead, Game.ActiveColor playerColor){
        boolean range1 = false;
        //check if opponent's piece lies on one space ahead
        if (board.get(oneSpaceAhead.getRow()).getSpace(oneSpaceAhead.getCell()).getPiece() != null) {

            Piece firstSpacePiece = board.get(oneSpaceAhead.getRow()).getSpace(oneSpaceAhead.getCell()).getPiece();
            //System.out.println(firstSpacePiece.getColor() + " - color of the first space piece");
            //System.out.println(playerColor + " - the active color");
            //System.out.println(oneSpaceAhead.getRow() + " - the row of one ahead");
            //System.out.println(oneSpaceAhead.getCell() + " - the cell of one ahead");
            if ((firstSpacePiece.getColor() == Piece.Color.RED && playerColor == Game.ActiveColor.WHITE) || (firstSpacePiece.getColor() == Piece.Color.WHITE && playerColor == Game.ActiveColor.RED)){

                range1 = true;

            }
        }
        //check if the space diagonal to one space ahead is free
        boolean range2 = board.get(twoSpacesAhead.getRow()).getSpace(twoSpacesAhead.getCell()).getPiece() == null;
        //System.out.println(oneSpaceAhead.getRow() + " - row, " + oneSpaceAhead.getCell() + " - col, " + range1 + " - range1, " + range2 + " - range2 ");
        return range1 && range2;
    }

}

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

public class BoardView implements Iterable<Row> {

    /** The size of the checkers board (square) */
    static final int BOARD_SIZE = 8;

    private int redPiecesLeft = 12;

    private int whitePiecesLeft = 12;

    /** An ArrayList containing the Row objects that make up the checkers board */
    private final ArrayList<Row> board;

    public enum JumpType {
        FORWARD_LEFT,
        FORWARD_RIGHT,
        BACKWARD_RIGHT,
        BACKWARD_LEFT
    }

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
    public ArrayList<Row> getBoard() { return board; }

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

        if (move.isJumpMove()) {

            // Gets the Position of the captured Piece
            int capturedCell = (start.getCell() + end.getCell()) / 2;
            int capturedRow = (start.getRow() + end.getRow()) / 2;

            // There is now 1 less Piece of the Color of the captured Piece
            if (getPiece(capturedRow, capturedCell).getColor() == Piece.Color.RED)
                redPiecesLeft--;
            else
                whitePiecesLeft--;

            // Removes the captured Piece from the board
            board.get(capturedRow).getSpace(capturedCell).removePiece();
        }

        // Gets the Piece in focus
        Piece piece = startSpace.getPiece();

        // If the Move results in the Piece reaching any end of the board - it becomes a King
        if (end.getRow() == 0 || end.getRow() == (BOARD_SIZE - 1))
            piece.makeKing();

        // Removes the Piece from the starting Space and places is on the ending Space
        startSpace.removePiece();
        endSpace.placePiece(piece);
    }

    public int getRemainingPieces(Piece.Color color) {
        return (color == Piece.Color.RED ? redPiecesLeft : whitePiecesLeft);
    }

    public Piece getPiece(int row, int space) { return board.get(row).getSpace(space).getPiece(); }

    public boolean isJumpPossible(Piece currentPiece, int row, int space, JumpType jumpType) {

        switch (jumpType) {
            case FORWARD_LEFT:
                if (space > 1) {
                    Piece leftPiece = getPiece(row - 1, space - 1);

                    if (leftPiece != null && !currentPiece.sameColorAs(leftPiece) &&
                            getPiece((row - 2), (space - 2)) == null)
                        return true;
                }

                break;
            case BACKWARD_LEFT:
                if (space > 1) {
                    Piece leftPiece = getPiece(row + 1, space - 1);

                    if (leftPiece != null && !currentPiece.sameColorAs(leftPiece) &&
                            getPiece((row + 2), (space - 2)) == null)
                        return true;
                }

                break;
            case FORWARD_RIGHT:
                if (space < (BOARD_SIZE - 2)) {
                    Piece rightPiece = getPiece(row - 1, space + 1);

                    if (rightPiece != null && !currentPiece.sameColorAs(rightPiece) &&
                            getPiece((row - 2), (space + 2)) == null)
                        return true;
                }

                break;
            case BACKWARD_RIGHT:
                if (space < (BOARD_SIZE - 2)) {
                    Piece rightPiece = getPiece(row + 1, space + 1);

                    if (rightPiece != null && !currentPiece.sameColorAs(rightPiece) &&
                            getPiece((row + 2), (space + 2)) == null)
                        return true;
                }

                break;
        }

        return false;
    }

    private boolean checkJumpPossibilities(Piece currentPiece, int row, int space) {

        // Forward Jump
        if (row > 1) {
            boolean canJumpLeft = isJumpPossible(currentPiece, row, space, JumpType.FORWARD_LEFT);
            boolean canJumpRight = isJumpPossible(currentPiece, row, space, JumpType.FORWARD_RIGHT);

            if (canJumpLeft || canJumpRight)
                return true;
        }

        // Backward Jump
        if (currentPiece.getType() == Piece.Type.KING && row < (BOARD_SIZE - 2)) {
            boolean canJumpLeft = isJumpPossible(currentPiece, row, space, JumpType.BACKWARD_LEFT);
            boolean canJumpRight = isJumpPossible(currentPiece, row, space, JumpType.BACKWARD_RIGHT);

            return canJumpLeft || canJumpRight;
        }

        return false;
    }

    public boolean isRequiredToJump(Game.ActiveColor activeColor, Turn turn) {
        Piece.Color activePieceColor = (activeColor == Game.ActiveColor.RED ? Piece.Color.RED : Piece.Color.WHITE);

        if (!turn.hasMoves()) {

            for (int row = 0; row < BOARD_SIZE; row++) {

                for (int space = 0; space < BOARD_SIZE; space++) {
                    Piece currentPiece = getPiece(row, space);

                    if (currentPiece != null && currentPiece.getColor() == activePieceColor &&
                            checkJumpPossibilities(currentPiece, row, space))
                        return true;
                }
            }
        }
        else if (turn.getLastMove().isJumpMove()) {
            Move lastMove = turn.getLastMove();

            int row = lastMove.getEnd().getRow();
            int space = lastMove.getEnd().getCell();

            Piece currentPiece = getPiece(row, space);

            return currentPiece != null && currentPiece.getColor() == activePieceColor &&
                    checkJumpPossibilities(currentPiece, row, space);
        }

        return false;
    }

    public void recoverMove(Move move) {
        Position start = move.getEnd();
        Position end = move.getStart();

        Space startSpace = board.get(start.getRow()).getSpace(start.getCell());
        Space endSpace = board.get(end.getRow()).getSpace(end.getCell());

        Piece piece = startSpace.getPiece();

        startSpace.removePiece();
        endSpace.placePiece(piece);

        // Recover Jump
        if (move.isJumpMove()) {
            int capturedCell = (start.getCell() + end.getCell()) / 2;
            int capturedRow = (start.getRow() + end.getRow()) / 2;

            Piece recoveredPiece =
                    new Piece((piece.getColor() == Piece.Color.RED ? Piece.Color.WHITE : Piece.Color.RED));

            if (recoveredPiece.getColor() == Piece.Color.RED)
                redPiecesLeft++;
            else
                whitePiecesLeft++;

            board.get(capturedRow).getSpace(capturedCell).placePiece(recoveredPiece);
        }
    }

    public void resetBoard() {

        //remove all the pieces
        for (Row row: board){
            for (Space space : row.getSpaces()){
                space.removePiece();
            }
        }

        //Place the pieces in the appropriate place
        for (Row row: board){
            row.setRow(row.generateRow(row.getLeadingColor(), row.getBottomColor()));
        }
    }

}

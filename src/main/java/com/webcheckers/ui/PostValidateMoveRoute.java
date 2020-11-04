package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;

/**
 * This class handles the /validateMove route
 * Used for allowing player moves to happen (and later validating if the move was legal)
 * Added Jump move functionality
 *
 * @author Joel Clyne, Dmitry Selin
 */
public class PostValidateMoveRoute implements Route {

    private final GameCenter gameCenter;

    private enum MoveType {
        FORWARD_SINGLE,
        FORWARD_JUMP,
        BACKWARD_SINGLE,
        BACKWARD_JUMP,
        INVALID
    }

    public PostValidateMoveRoute(GameCenter gameCenter) { this.gameCenter = gameCenter; }

    private MoveType getMoveType(int rowDifference, int colDifference) {
        int absColDifference = Math.abs(colDifference);

        if (rowDifference == -1 && absColDifference == 1)
            return MoveType.FORWARD_SINGLE;

        else if (rowDifference == -2 && absColDifference == 2)
            return MoveType.FORWARD_JUMP;

        else if (rowDifference == 1 && absColDifference == 1)
            return MoveType.BACKWARD_SINGLE;

        else if (rowDifference == 2 && absColDifference == 2)
            return MoveType.BACKWARD_JUMP;

        return MoveType.INVALID;
    }

    private Message validateSingleMove(Turn turn, Move move, BoardView currentBoard, Game.ActiveColor activeColor) {

        if (currentBoard.isRequiredToJump(activeColor, turn))
            return Message.error("INVALID MOVE: A jump move can be taken this turn.");

        currentBoard.makeMove(move);
        turn.addValidMove(move);

        return Message.info("Valid Move");
    }

    private Message validateJumpMove(Turn turn, Move move, BoardView currentBoard, BoardView.JumpType jumpType) {
        int row = move.getStart().getRow();
        int space = move.getStart().getCell();

        Piece currentPiece = currentBoard.getPiece(row, space);

        if (!currentBoard.isJumpPossible(currentPiece, row, space, jumpType))
            return Message.error("INVALID MOVE: Not a valid jump move.");

        currentBoard.makeMove(move);
        turn.addValidMove(move);

        return Message.info("Valid Move");
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        //get the move from actionData as JSON string
        //convert moveJSON to move Object using Gson
        Move move = new Gson().fromJson(request.queryParams("actionData"), Move.class);

        Game game = gameCenter.getGame(session.attribute(GAME_ID_ATTR));
        Game.ActiveColor activeColor = game.getActiveColor();
        BoardView currentBoard = game.getActivePlayerBoard();
        Turn turn = game.getCurrentTurn();

        int rowDifference = move.getRowDifference();
        int colDifference = move.getColDifference();

        MoveType moveType = getMoveType(rowDifference, colDifference);

        Piece currentPiece;
        Message message = null;

        if (!turn.hasMoves() || currentBoard.isRequiredToJump(activeColor, turn)) {

            switch (moveType) {
                case FORWARD_SINGLE:
                    message = validateSingleMove(turn, move, currentBoard, activeColor);
                    break;

                case FORWARD_JUMP:
                    if (colDifference == -2)
                        message = validateJumpMove(turn, move, currentBoard, BoardView.JumpType.FORWARD_LEFT);
                    else
                        message = validateJumpMove(turn, move, currentBoard, BoardView.JumpType.FORWARD_RIGHT);
                    break;

                case BACKWARD_SINGLE:
                    currentPiece = currentBoard.getPiece(move.getStart().getRow(), move.getStart().getCell());

                    if (currentPiece.isKing())
                        message = validateSingleMove(turn, move, currentBoard, activeColor);
                    else
                        message = Message.error("INVALID MOVE: Only kings can move backwards");
                    break;

                case BACKWARD_JUMP:
                    currentPiece = currentBoard.getPiece(move.getStart().getRow(), move.getStart().getCell());

                    if (currentPiece.isKing()) {

                        if (colDifference == -2)
                            message = validateJumpMove(turn, move, currentBoard, BoardView.JumpType.BACKWARD_LEFT);
                        else
                            message = validateJumpMove(turn, move, currentBoard, BoardView.JumpType.BACKWARD_RIGHT);
                    }
                    else
                        message = Message.error("INVALID MOVE: Only kings can move backwards");

                    break;

                case INVALID:
                    message = Message.error("INVALID MOVE: Not diagonal");
            }
        }
        else
            message = Message.error("INVALID MOVE: Can only move once");

        return new Gson().toJson(message);
    }
}

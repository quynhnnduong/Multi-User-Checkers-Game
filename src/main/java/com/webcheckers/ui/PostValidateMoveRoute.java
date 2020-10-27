package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;
import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;

/**
 * This class handles the /validateMove route
 * Used for allowing player moves to happen (and later validating if the move was legal)
 * Added Jump move functionality
 * @author Joel Clyne, Dmitry Selin
 */
public class PostValidateMoveRoute implements Route {

    private final GameCenter gameCenter;

    public PostValidateMoveRoute(GameCenter gameCenter) { this.gameCenter = gameCenter; }

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        //get the move from actionData as JSON string
        String moveJSON = request.queryParams("actionData");

        //convert moveJSON to move Object using Gson
        Move move = new Gson().fromJson(moveJSON, Move.class);
        Turn turn = gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn();

        Position startPosition = (!turn.hasMoves() ? move.getStart() : turn.getFirstMove().getStart());
        Position endPosition = move.getEnd();

        int rowDifference = Math.abs(endPosition.getCell() - startPosition.getCell());
        int colDifference = startPosition.getRow() - endPosition.getRow();

        Message message;

        if (turn.hasMoves())
            message = Message.error("INVALID MOVE: Can only move once");
        else if (colDifference < 1)
            message = Message.error("INVALID MOVE: Cannot move backwards");
        else if (rowDifference != 1 || colDifference > 1) {
            //check if the move was a jump
            //remove the captured piece
            if (rowDifference == 2 && colDifference == 2){
                int capturedCell = (startPosition.getCell() + endPosition.getCell()) / 2;
                int capturedRow = (startPosition.getRow() + endPosition.getRow()) / 2 ;
                Space jumpedSpace = null;
                //get the view of the active player's turn
                if (gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getActiveColor() == Game.ActiveColor.RED) {
                    jumpedSpace = gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getRedView().getBoard().get(capturedRow).getSpace(capturedCell);
                } else {
                    jumpedSpace = gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getWhiteView().getBoard().get(capturedRow).getSpace(capturedCell);
                }
                //check if there was a piece on the jumped space
                if (jumpedSpace.getPiece() != null) {
                    //if there was the move was a jump
                    //now check to see if a player is trying to jump over their own piece
                    Piece.Color pieceColor = jumpedSpace.getPiece().getColor();
                    Game.ActiveColor playerColor = gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getActiveColor();
                    System.out.println(pieceColor);
                    System.out.println(playerColor);
                    if ((pieceColor == Piece.Color.RED && playerColor == Game.ActiveColor.RED) || (pieceColor == Piece.Color.WHITE && playerColor == Game.ActiveColor.WHITE)) {
                        //if they are they can't do that
                        message = Message.error("INVALID MOVE: Cannot capture your own piece");
                    } else {
                        message = Message.info("Valid Move");
                        turn.addValidMove(move);
                    }
                } else {
                    message = Message.error("INVALID MOVE: Not directly diagonal");
                }

            } else {
                message = Message.error("INVALID MOVE: Not directly diagonal");
            }
                    //board.get(capturedRow).getSpace(capturedCell);
        }
        else {
            message = Message.info("Valid Move");
            turn.addValidMove(move);
        }

        return new Gson().toJson(message);
    }
}

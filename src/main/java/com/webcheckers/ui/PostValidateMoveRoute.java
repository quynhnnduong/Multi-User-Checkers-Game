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

        //Position startPosition = (!turn.hasMoves() ? move.getStart() : turn.getLastMove().getStart());
        Position startPosition = move.getStart();
        Position endPosition = move.getEnd();

        int rowDifference = Math.abs(endPosition.getCell() - startPosition.getCell());
        int colDifference = startPosition.getRow() - endPosition.getRow();

        System.out.println("row diff v3- " + rowDifference);
        System.out.println("col diff v3- " + colDifference);


        BoardView redView = gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getRedView();
        BoardView whiteView = gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getWhiteView();

        Game.ActiveColor currentColor = gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getActiveColor();



        BoardView currentView;
        if (currentColor == Game.ActiveColor.RED) {
            currentView = redView;
        } else {
            currentView = whiteView;
        }

        //used to check if the piece was originally a king
        Piece startPiece = currentView.getBoard().get(startPosition.getRow()).getSpace(startPosition.getCell()).getPiece();
        if (startPiece == null){
            startPiece = currentView.getPieceByPosition(turn.getLastStartPosition());
        }

        System.out.println(" start row " + startPosition.getRow() + ", start col " + startPosition.getCell());

        if (startPiece.getType() == Piece.Type.KING){
            colDifference = Math.abs((colDifference));
        }

        Message message;

        //if there was no last move, then any move is ok
        boolean canMakeMove = true;
        //if there was a last move, then another move is only ok if the last move was a jump and you're going to jump again
        //check if there was a last move
        if (turn.hasMoves()){
            canMakeMove = currentView.isLastMoveAndNextMoveJump(turn, move);
        }

        if (!canMakeMove) {
            //check if the last move was a jump
            //boolean lastMoveWasJump = currentView.isMoveJump(turn);
            message = Message.error("INVALID MOVE: You can only jump after another jump");
        } else if (colDifference < 1)
            message = Message.error("INVALID MOVE: Single pieces cannot move backwards");
        else if (rowDifference != 1 || colDifference > 1) {
            //check if the move was a jump
            if (rowDifference == 2 && colDifference == 2){
                int capturedCell = (startPosition.getCell() + endPosition.getCell()) / 2;
                int capturedRow = (startPosition.getRow() + endPosition.getRow()) / 2 ;
                //System.out.println("Captured Cell - " +capturedCell);
                //System.out.println("Captured Row - " + capturedRow);
                Space jumpedSpace = null;
                //get the view of the active player's turn
                jumpedSpace = currentView.getBoard().get(capturedRow).getSpace(capturedCell);
                //check if there was a piece on the jumped space
                if (jumpedSpace.getPiece() != null) {
                    //if there was the move was a jump
                    Piece.Color pieceColor = jumpedSpace.getPiece().getColor();
                    //Game.ActiveColor playerColor = gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getActiveColor();
                    //now check to see if a player is trying to jump over their own piece
                    if ((pieceColor == Piece.Color.RED && currentColor == Game.ActiveColor.RED) || (pieceColor == Piece.Color.WHITE && currentColor == Game.ActiveColor.WHITE)) {
                        //if they are they can't do that
                        message = Message.error("INVALID MOVE: Cannot capture your own piece");
                    } else {
                        message = Message.info("You captured a piece!");
                        turn.addValidMove(move);
                    }
                } else {
                    message = Message.error("INVALID MOVE: Not directly diagonal");
                }

            } else {
                message = Message.error("INVALID MOVE: Not directly diagonal");
            }
                    //board.get(capturedRow).getSpace(capturedCell);
        } else if(currentView.checkForJumpAcrossBoard(currentColor)){
            // check if tbe player is able to jump and didn't
            message = Message.error("You have an available move, so just do it");
        }
        else {
            message = Message.info("Valid Move");
            turn.addValidMove(move);
        }

        return new Gson().toJson(message);
    }

    
}

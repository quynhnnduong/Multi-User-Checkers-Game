package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import com.webcheckers.model.Turn;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;

/**
 * This class handles the /validateMove route
 * Used for allowing player moves to happen (and later validating if the move was legal)
 *
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
        else if (rowDifference != 1 || colDifference > 1)
            message = Message.error("INVALID MOVE: Not directly diagonal");
        else {
            message = Message.info("Valid Move");
            turn.addValidMove(move);
        }

        return new Gson().toJson(message);
    }
}

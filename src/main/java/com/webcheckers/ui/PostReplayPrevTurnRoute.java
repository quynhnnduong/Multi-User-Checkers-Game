package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;


/**
 * Handles moving to the next previous of a replay
 * @author Joel Clyne
 */
public class PostReplayPrevTurnRoute implements Route {


    public PostReplayPrevTurnRoute(TemplateEngine templateEngine, ReplayLoader replayLoader){
    }

    @Override
    public Object handle(Request request, Response response){
        final Session session = request.session();
        Replay replay = session.attribute(REPLAY_COPY);

        //get the last move
        ReplayMove lastMove = replay.getCurrentTurn();

        BoardView spectatorView = session.attribute(REPLAY_BOARD);
        BoardView fakeWhite = session.attribute(REPLAY_WHITE_VIEW);

        //reverse it so it undoes what happened before
        Move undo = lastMove.getMove().getUndoMove();
        replay.executeReplayMove(lastMove.getPlayerColor(), undo, spectatorView, fakeWhite);

        //check if its a jump
        if(undo.doesMoveSkipOverSpace()){
            Game.ActiveColor currentColor = lastMove.getPlayerColor();
            //if it is place a piece back in the space that was jumped over
            if (currentColor == Game.ActiveColor.RED){
                Space jumpedSpace = undo.getSpaceInMiddle(spectatorView);
                jumpedSpace.placePiece(new Piece(Piece.Color.WHITE));
            } else if (currentColor == Game.ActiveColor.WHITE){
                Space jumpedSpace = undo.getSpaceInMiddle(fakeWhite);
                jumpedSpace.placePiece(new Piece(Piece.Color.RED));
            }
        }

        //get the previous turn of the replay
        replay.decrementTurn();

        return new Gson().toJson(Message.info("true"));
    }
}

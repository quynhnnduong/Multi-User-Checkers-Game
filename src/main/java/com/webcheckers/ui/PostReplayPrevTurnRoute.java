package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;

public class PostReplayPrevTurnRoute implements Route {

    /** The Template Engine that generates the '.ftl' pages */
    private final TemplateEngine templateEngine;

    //private final ReplaySaver replaySaver;

    private final ReplayLoader replayLoader;

    public PostReplayPrevTurnRoute(TemplateEngine templateEngine, ReplayLoader replayLoader){
        this.templateEngine = templateEngine;
        //this.replaySaver = replaySaver;
        this.replayLoader = replayLoader;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Player currentUser = session.attribute(PLAYER_ATTR);
        //currentUser.startSpectating();
        //TODO add logic for getting the turn from the replay loader
        String gameId = request.queryParams("gameID");
        Replay replay = replayLoader.getReplay(gameId);

        //get the last move
        ReplayMove lastMove = replay.getCurrentTurn();

        Game game = replay.getGame();
        BoardView redView = game.getRedView();
        BoardView whiteView = game.getWhiteView();
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

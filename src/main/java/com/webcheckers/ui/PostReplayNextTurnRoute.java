package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.UIProtocol.*;

public class PostReplayNextTurnRoute implements Route {

    /** The Template Engine that generates the '.ftl' pages */
    private final TemplateEngine templateEngine;

    //private final ReplaySaver replaySaver;

    private final ReplayLoader replayLoader;

    public PostReplayNextTurnRoute(TemplateEngine templateEngine, ReplayLoader replayLoader){
        this.templateEngine = templateEngine;
        this.replayLoader = replayLoader;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Player currentUser = session.attribute(PLAYER_ATTR);
        //currentUser.startSpectating();
        //TODO add logic for getting the turn from the replay loader
        String gameId = request.queryParams("gameID");
        Replay replay = session.attribute(REPLAY_COPY);

        //get the next turn of the replay
        replay.incrementTurn();
        if (replay.getCurrentTurnNum() >= 0){
            ReplayMove replayMove = replay.getCurrentTurn();
            replay.executeReplayMove(replayMove.getPlayerColor(), replayMove.getMove(), session.attribute(REPLAY_BOARD), session.attribute(REPLAY_WHITE_VIEW));
        }
        return new Gson().toJson(Message.info("true"));
    }


}

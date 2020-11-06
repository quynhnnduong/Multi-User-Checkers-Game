package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.UIProtocol.*;

/**
 * Handles moving to the next turn of a replay
 * @author Joel Clyne
 */
public class PostReplayNextTurnRoute implements Route {

    public PostReplayNextTurnRoute(TemplateEngine templateEngine, ReplayLoader replayLoader){
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
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

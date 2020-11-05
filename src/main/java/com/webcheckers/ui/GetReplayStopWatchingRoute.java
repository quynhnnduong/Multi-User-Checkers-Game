package com.webcheckers.ui;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;
import static spark.Spark.halt;

public class GetReplayStopWatchingRoute implements Route {

    /** The Template Engine that generates the '.ftl' pages */
    //private final TemplateEngine templateEngine;

    public GetReplayStopWatchingRoute(TemplateEngine templateEngine){

        //this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Player currentUser = session.attribute(PLAYER_ATTR);
        session.attribute(REPLAY_COPY, null);
        BoardView sessionBoard = session.attribute(REPLAY_BOARD);
        sessionBoard.resetBoard();
        BoardView fakeBoard = session.attribute(REPLAY_WHITE_VIEW);
        fakeBoard.resetBoard();
        currentUser.stopSpectating();
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}

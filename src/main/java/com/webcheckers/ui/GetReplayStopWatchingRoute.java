package com.webcheckers.ui;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;
import static spark.Spark.halt;

/**
 * Handles exiting a replay game
 * @author Joel Clyne
 */
public class GetReplayStopWatchingRoute implements Route {

    public GetReplayStopWatchingRoute(TemplateEngine templateEngine){
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();

        //remove the replay the user currently views
        Player currentUser = session.attribute(PLAYER_ATTR);
        session.attribute(REPLAY_COPY, null);

        //reset the spectator and fake white boards
        BoardView sessionBoard = session.attribute(REPLAY_BOARD);
        sessionBoard.resetBoard();
        BoardView fakeBoard = session.attribute(REPLAY_WHITE_VIEW);
        fakeBoard.resetBoard();

        //make the user stop spectating
        currentUser.stopSpectating();

        //redirect to the home page
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}

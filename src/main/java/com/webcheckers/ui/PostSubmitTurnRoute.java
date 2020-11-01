package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;

/**
 * Route for determining if the turn is valid
 *
 * @author Dmitry Selin
 */
public class PostSubmitTurnRoute implements Route {

    private final GameCenter gameCenter;

    public PostSubmitTurnRoute(GameCenter gameCenter) { this.gameCenter = gameCenter; }

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        Game game = gameCenter.getGame(session.attribute(GAME_ID_ATTR));



        if (game != null && game.getCurrentTurn().hasMoves()) {

            game.endTurn();
            return new Gson().toJson(Message.info("Successfully Submitted Turn"));
        }

        return new Gson().toJson(Message.error("Turn cannot be submitted"));
    }
}

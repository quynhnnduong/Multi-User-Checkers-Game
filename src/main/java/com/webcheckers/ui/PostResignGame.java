package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;

/**
 * Handles the Resign button in the controls.
 *
 * @author Shubhang Mehrotra, Dmitry Selin
 */
public class PostResignGame implements Route {

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        Player currentPlayer = session.attribute(PLAYER_ATTR);
        currentPlayer.resign();

        return new Gson().toJson(Message.info(currentPlayer.getName() + " resigned."));
    }
}

package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;

/**
 * Handles the Resign button in the controls.
 * @author Shubhang Mehrotra, Joel Clyne
 */
public class PostResignGame implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    private final TemplateEngine templateEngine;


    final Message RESIGN_MSG = Message.info("Player Resigned");


    /**
     * Constructor for PostResignGame
     * @param templateEngine
     */
    public PostResignGame(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        LOG.config("PostResignGame is initialized.");
    }

    //interfaces and abstract classes: In Model tier.

    // classes should be—
    // Open: to extension, and
    // Closed: to modification.

    // Have enum for player type; makes it easier to check.


    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Gson gson = new Gson();

        //Remove indication that current player is part of a game
        Player currentPlayer = session.attribute(PLAYER_ATTR);
        currentPlayer.stopTurn();
        currentPlayer.exitGame();

        return gson.toJson(RESIGN_MSG);
    }
}

package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Handles the Resign button in the controls.
 * @author Shubhang Mehrotra
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
        return gson.toJson(RESIGN_MSG);
    }
}

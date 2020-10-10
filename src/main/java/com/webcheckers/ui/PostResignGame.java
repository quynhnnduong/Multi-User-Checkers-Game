package com.webcheckers.ui;

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

    /**
     * Constructor for PostResignGame
     * @param templateEngine
     * @param playerLobby
     */
    public PostResignGame(TemplateEngine templateEngine, PlayerLobby playerLobby) {
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

        LOG.finer("PostResignGame is invoked.");
        Map<String, Object> vm = new HashMap<>();

        vm.put("title", "Resign Game");

        return templateEngine.render(new ModelAndView(vm , "resignGame.ftl"));
    }
}

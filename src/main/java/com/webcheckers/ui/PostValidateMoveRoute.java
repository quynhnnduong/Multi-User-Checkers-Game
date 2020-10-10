package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class handles the /validateMove route
 * Used for allowing player moves to happen (and later validating if the move was legal)
 * @author Joel Clyne
 */
public class PostValidateMoveRoute implements Route {
    /** A logger used to send messages to the console - for testing purposes */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    /** The Template Engine that generates the '.ftl' pages */
    private final TemplateEngine templateEngine;

    /** A server-wide Player Lobby that keeps track of all the players waiting to start a game */
    private final PlayerLobby playerLobby;

    public PostValidateMoveRoute(TemplateEngine templateEngine, PlayerLobby playerLobby){
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();

        Map<String, Object> vm = new HashMap<>();
        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}

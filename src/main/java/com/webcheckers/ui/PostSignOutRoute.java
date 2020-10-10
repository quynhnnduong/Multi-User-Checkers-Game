package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.LEGIT_OPPONENT;

/**
 * @author Sasha Persaud (srp4581)
 */
public class PostSignOutRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());
    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;


    public static final String PLAYER_NAME_ATTR = "playerName";
    public static final String PLAYERLOBBY_KEY = "playerLobby";
    private static final String LOGGED_IN_ATTR = "loggedIn";
    private static final Message INVALID_NAME_MSG = Message.info("Invalid Name. Please try again.");

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST/} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */

    public PostSignOutRoute(final TemplateEngine templateEngine, final GameCenter gameCenter, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;

        LOG.config("PostSignOutRoute is initialized.");
    }

    /**
     * @author Sasha Persaud
     * Sign the player out and render the Sign In page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the Sign In HTML page TODO or maybe the welcome page? -Sasha
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostSignOutRoute is invoked.");

        // Retrieve the current game object.
        final Session session = request.session();

        // Create the view-model map and add values that will be displayed in the sign in page.
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Sign in");
        vm.put("message", Message.info("Sign in with your username."));

        // Clean up the current player's presence in the game.
        // Remove from Player Lobby.
        // Update count if Player Lobby doesn't do this automatically.


        // render the View
        return templateEngine.render(new ModelAndView(vm , "signIn.ftl"));
    }
}

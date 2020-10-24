package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;
import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;

/**
 * @author Sasha Persaud (srp4581), Joel Clyne
 */
public class PostSignOutRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;

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
     * @return the Sign In HTML page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignOutRoute is invoked.");

        // Retrieve the current game object.
        final Session session = request.session();

        // Get the current user
        Player currentPlayer = session.attribute(PLAYER_ATTR);
        currentPlayer.exitGame();

        String signedOut = request.queryParams("sign_out");

        System.out.println("QueryParams: " + signedOut);

        // Create the view-model map and add values that will be displayed in the sign in page.
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Sign Out");
        vm.put("message", Message.info("Are you sure you want to sign out?"));

        // Clean up the current player's presence in the game.
        playerLobby.removePlayer(currentPlayer);

        // Remove the player from the session
        session.attribute(PLAYER_ATTR, null);

        // render the View
        return templateEngine.render(new ModelAndView(vm , "signout.ftl"));
    }
}

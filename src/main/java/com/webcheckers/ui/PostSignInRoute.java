package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.WELCOME_MSG;
import static com.webcheckers.ui.UIProtocol.*;
import static spark.Spark.halt;

/**
 * Class that defines the route that retrieves the App view after signing in.
 *
 * @author Joel Clyne (jmc4514), Dmitry Selin (des3358)
 */
public class PostSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostSignInRoute(final TemplateEngine templateEngine, final GameCenter gameCenter, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
        LOG.config("PostSignInRoute is initialized.");
    }

    /**
     * Render the Sign-in page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return the rendered HTML for the Sign In page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignInRoute is invoked.");

        // retrieve the game object
        final Session session = request.session();

        Map<String, Object> vm = new HashMap<>();

        //get the name from the text box
        Player currentPlayer = new Player(request.queryParams("text_field"));

        //adds the name to the playerlobby.
        if (!playerLobby.addPlayer(currentPlayer)) {

            //this flag shows the message that the last name they put was taken by someone else
            session.attribute(LEGIT_NAME_ATTR, false);

            response.redirect(WebServer.SIGNIN_URL);
            return halt();
        }

        session.attribute(PLAYER_ATTR, currentPlayer);
        session.attribute(LEGIT_NAME_ATTR, true);

        vm.put("title", "Welcome!");
        vm.put("message", WELCOME_MSG);
        vm.put("currentUser", currentPlayer);
        vm.put("playerList", playerLobby.getPlayers());
        vm.put("loggedIn", true);
        vm.put("playersMessage", playerLobby.getLobbyMessage());
        vm.put(LEGIT_OPPONENT_ATTR, session.attribute(LEGIT_NAME_ATTR));

        response.redirect(WebServer.HOME_URL);
        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }
}

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

import static com.webcheckers.ui.UIProtocol.*;
import static spark.Spark.halt;

/**
 * @author Joel Clyne (jmc4514)
 */
public class PostSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;

    private static final Message INVALID_NAME_MSG = Message.info("Invalid Name. Please try again.");

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSignInRoute(final TemplateEngine templateEngine, final GameCenter gameCenter, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
        LOG.config("PostSignInRoute is initialized.");
    }

    /**
     * @author Joel Clyne
     * Render the Sign-in page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign In page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignInRoute is invoked.");

        // retrieve the game object
        final Session session = request.session();
        Map<String, Object> vm = new HashMap<>();

        //if there is a session here - someone has already logged in
        if (session.attribute(PLAYERLOBBY_ATTR) != null) {

            //get the name from the text box
            final String name = request.queryParams("text_field");

            Player currentPlayer = new Player(name);

            //adds the name to the playerlobby.
            if (!playerLobby.addPlayer(currentPlayer)) {

                vm.put("message", INVALID_NAME_MSG);

                //this flag shows the message that the last name they put was taken by someone else
                session.attribute(LEGIT_NAME_ATTR, false);

                response.redirect(WebServer.SIGNIN_URL);
                return halt();
            }

            //this adds the the name of the player to the current session
            session.attribute(PLAYER_ATTR, currentPlayer);

            vm.put("title", "Welcome!");
            vm.put("playersMessage", playerLobby.getLobbyMessage());
            vm.put("playerList", playerLobby.getPlayers());
            vm.put(LOGGED_IN_ATTR, true);
            vm.put("currentUser", currentPlayer);
            response.redirect(WebServer.HOME_URL);
        }
        else {
            //panic bcs that's not supposed to happen
            //redirect to the homepage
            response.redirect(WebServer.HOME_URL);
        }

        //refresh the legit opponent bcs we're going to home.ftl
        vm.put(LEGIT_OPPONENT_ATTR, session.attribute(LEGIT_OPPONENT_ATTR));

        // render the View
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}

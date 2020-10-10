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

import static com.webcheckers.ui.GetHomeRoute.LEGIT_NAME;
import static com.webcheckers.ui.GetHomeRoute.LEGIT_OPPONENT;

/**
 * @author Joel Clyne (jmc4514)
 */
public class PostSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;


    public static final String PLAYER_NAME_ATTR = "playerName";
    public static final String PLAYERLOBBY_KEY = "playerLobby";
    private static final String LOGGED_IN_ATTR = "loggedIn";
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
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostSignInRoute is invoked.");

        // retrieve the game object
        final Session session = request.session();

        Map<String, Object> vm = new HashMap<>();
        //final PlayerLobby playerServices = session.attribute(GetHomeRoute.PLAYERSERVICES_KEY);

        //if there is a session here - someone has already logged in
        if (session.attribute(PLAYERLOBBY_KEY) != null){

            //get the name from the text box
            //for some reason it doesn't work with spaces and I have no clue why
            final String name = request.queryParams("text_field");

            vm.put("title", "Welcome!");

            // Save the player's name in the session
            session.attribute(PLAYER_NAME_ATTR, name);

            //adds the name to the playerlobby.
            if(!playerLobby.addPlayer(name)){
                vm.put("message", INVALID_NAME_MSG);
                //this flag shows the message that the last name they put was taken by someone else
                session.attribute(GetHomeRoute.LEGIT_NAME, false);

                response.redirect(WebServer.SIGNIN_URL);
            }

            vm.put("playersMessage", "");

            // TODO sign in the player instead of constructing player instances with default true for sign in
            vm.put(LOGGED_IN_ATTR, true);
            //the current player of the session is the current user
            vm.put("currentUser", playerLobby.getPlayer(session.attribute(PLAYER_NAME_ATTR)));

        } else {
            //panic bcs that's not supposed to happen
            //redirect to the homepage
            response.redirect(WebServer.HOME_URL);
        }
        //refresh the legit opponent bcs we're going to home.ftl
        vm.put(LEGIT_OPPONENT, session.attribute(LEGIT_OPPONENT));

        // render the View
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}

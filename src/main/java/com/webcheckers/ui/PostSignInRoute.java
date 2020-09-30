package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author Joel Clyne (jmc4514)
 */
public class PostSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    private final TemplateEngine templateEngine;


    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */

    public PostSignInRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("PostSignInRoute is initialized.");
    }

    /**
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

        /** TODO Get the name, create and add a new Player to the Player Lobby? (i don't think a route can create a player
         * Then after we redirect to home, put the players in the player lobby
         * to the vm and insert them into signin.ftl under the condition that
         * the state of the player/game is IsLoggedOn. Not sure. -Sasha
         */

        final String name = request.queryParams("text_field");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");
        vm.put("isLoggedIn", true);

        // render the View
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}

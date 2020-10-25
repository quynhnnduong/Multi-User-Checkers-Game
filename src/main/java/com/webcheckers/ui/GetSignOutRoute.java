package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetSignOutRoute implements Route {

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

    public GetSignOutRoute(final TemplateEngine templateEngine, final GameCenter gameCenter, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
        LOG.config("PostSignOutRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        // Create the view-model map and add values that will be displayed in the sign in page.
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Sign Out");
        vm.put("message", Message.info("Are you sure you want to sign out?"));

        LOG.finer("GetSignOutRoute is invoked.");


        return templateEngine.render(new ModelAndView(vm, "signout.ftl"));
    }
}

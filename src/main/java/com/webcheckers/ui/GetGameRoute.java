package com.webcheckers.ui;


import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

/**
 * The UI Controller to GET the Game page.
 * @author Shubhang Mehrotra(sm9943)
 * Added getting the correct player object for both players
 * @author Joel Clyne
 */
public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;


    private Message message = Message.info("Here"); //ignore for now (shubhang)

    private Player player2;
    private final BoardView boardView = new BoardView();

    private final HashMap<String, Object> map = null;


    enum viewMode {
        PLAY,
        SPECTATOR,
        REPLAY
    }

    enum activeColor{
        RED,
        WHITE
    }

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        //
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Render the Game page
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        //gets the current player
        final Session session = request.session();

        //makes the current player the red player
        Player player = playerLobby.getPlayer(session.attribute(PLAYER_NAME_ATTR));
        Player redPlayer = player;

        //gets the player2 from the url param opponent and makes them the white player
        Player player2 = playerLobby.getPlayer(request.queryParams("opponent"));
        Player whitePlayer = player2;

        //TODO find out proper way to assign red and blue players

        LOG.finer("GetGameRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game");
        vm.put("currentUser", player);
        vm.put("loggedIn", true);
        vm.put("viewMode", viewMode.PLAY);
        vm.put("modeOptionsAsJSON", map);
        vm.put("redPlayer", redPlayer);
        vm.put("whitePlayer", whitePlayer);
        vm.put("activeColor", activeColor.RED);
        vm.put("board", boardView);
        vm.put("message", message);

        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}

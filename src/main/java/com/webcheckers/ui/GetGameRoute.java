package com.webcheckers.ui;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Game page.
 * @author Shubhang Mehrotra(sm9943)
 */
public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    private final TemplateEngine templateEngine;

    private Message message = Message.info("Here"); //ignore for now (shubhang)

    private final Player player = new Player("shubh");  //just to check stuff (shubhang)
    private final Player player2 = new Player("shubhang");
    private final BoardView  boardView = new BoardView();

    private final HashMap<String, Object> map = null;


    private final Player redPlayer = player;
    private final Player whitePlayer = player2;

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
    public GetGameRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Render the Game page.
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
        final Session session = request.session();

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

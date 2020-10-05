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
        Player redPlayer;

        //gets the player2 from the url param opponent and makes them the white player
        Player player2 = playerLobby.getPlayer(request.queryParams("opponent"));
        Player whitePlayer;

        LOG.finer("GetGameRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();


        //System.out.println(player2.getName());
        //check if the opponent is not currently playing a game
        if (player2.getIsMidGame() && !player2.getCallingPlayer()){
            //redirect and tell the current user that the person they picked is facing someone else
            session.attribute(GetHomeRoute.LEGIT_OPPONENT, false);
            //response.redirect(WebServer.HOME_URL);
        } else {


            //this makes the current player's state be in game according to the httpSession
            //for showing the error message of choosing to play against someone who's already in game
            session.attribute(GetHomeRoute.MID_GAME_KEY, true);

            //set the current and opponent's states to playing in playerLobby
            playerLobby.startPlayer(player.getName());
            //set player 2 to be called for a game
            player2.startCallingPlayer();
            //

            //then tell playerLobby those two players are opponents
            playerLobby.setOpponentMatch(player, player2);

            //TODO whenever we get to coding the win state, set all these to not playing, and remove the opponents from each other
        }

        //check if current player came in from a refresh of home.ftl - aka someone clicked on them and now they're being forced into a game
        //this is the only situation where they would already be mid game
        if (player.getIsMidGame()){
            //make the opponent the red player
            redPlayer = player2;
            //and the current player white
            whitePlayer = player;
        } else {
            //put them in the midgame state
            playerLobby.startPlayer(player2.getName());
            //no longer called
            player2.stopCallingPlayer();
            //vice versa
            redPlayer = player;
            whitePlayer = player2;
        }


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
        System.out.println(player2.getName());
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}

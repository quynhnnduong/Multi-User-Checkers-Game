package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

/**
 * This UI component represents the action of Players joining a checkers game. The
 * handle() function executes when a Player clicks the 'Start a Game' button next to an
 * opponent's name. The checkers game screen (game.ftl) should be visible after the execution
 * (if the opponent has not joined a game already).
 *
 * @author Shubhang Mehrotra, Joel Clyne, Dmitry Selin
 */
public class GetGameRoute implements Route {

    /** A logger used to send messages to the console - for testing purposes */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    /** The Template Engine that generates the '.ftl' pages */
    private final TemplateEngine templateEngine;

    /** A server-wide Player Lobby that keeps track of all the players waiting to start a game */
    private final PlayerLobby playerLobby;

    /** A model-tier component that houses and represents the checkers game */
    private BoardView boardView;

    //TODO Add  functionality and documentation to map
    private final HashMap<String, Object> map = null;

    /** An enumeration of the mode selected by the user to enter into */
    enum viewMode {
        PLAY,
        SPECTATOR,
        REPLAY
    }

    /** The color (representing a Player) whose turn it is currently */
    enum activeColor{
        RED,
        WHITE
    }

    /**
     * Creates the Spark Route that handles all the "GET /game" requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        boardView = new BoardView();
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * This method is called to handle the "GET /game" request. Whenever a Player
     * calls an opponent in for a game, this method handles the rendering of the game page
     * (game.ftl).
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();

        // Gets the current player
        Player currentPlayer = playerLobby.getPlayer(session.attribute(PLAYER_NAME_ATTR));

        // Gets the opponent from the URL and makes them the WHITE player
        Player opponent = playerLobby.getPlayer(request.queryParams("opponent"));

        // Logs a FINER invocation message
        LOG.finer("GetGameRoute is invoked.");

        // Checks if opponent is not in an existing game
        if (opponent.getIsMidGame() && !opponent.getCallingPlayer()) {

            // Redirect and inform currentPlayer that their opponent is playing in a different game
            session.attribute(GetHomeRoute.LEGIT_OPPONENT, false);
            //response.redirect(WebServer.HOME_URL);
        }
        else {

            // Sets the currentPlayer's state to be MID_GAME
            session.attribute(GetHomeRoute.MID_GAME_KEY, true);

            // Set currentPlayer and opponent's states to PLAYING in playerLobby
            playerLobby.startPlayer(currentPlayer.getName());

            // Start calling opponent to the game
            opponent.startCallingPlayer();

            // Inform PlayerLobby that currentPlayer and opponent are now playing
            playerLobby.setOpponentMatch(currentPlayer, opponent);

            //TODO whenever we get to coding the w in state, set all these to not playing, and remove the opponents from each other
        }

        Player redPlayer;
        Player whitePlayer;

        // Check if currentPlayer was called into a game by opponent
        if (currentPlayer.getIsMidGame()){

            // Opponent becomes the red Player since opponent started the game
            redPlayer = opponent;
            whitePlayer = currentPlayer;
        }
        else {

            // Both Players get put into the MID_GAME state
            playerLobby.startPlayer(opponent.getName());
            opponent.stopCallingPlayer();

            // CurrentPlayer called opponent, and therefore becomes the red Player
            redPlayer = currentPlayer;
            whitePlayer = opponent;
        }

        // Creates the HashMap to house all the freemarker components
        Map<String, Object> vm = new HashMap<>();

        // Adds all freemarker components to the HashMap
        vm.put("title", "Game");
        vm.put("currentUser", currentPlayer);
        vm.put("loggedIn", true);
        vm.put("viewMode", viewMode.PLAY);
        vm.put("modeOptionsAsJSON", map);
        vm.put("redPlayer", redPlayer);
        vm.put("whitePlayer", whitePlayer);
        vm.put("activeColor", activeColor.RED);

        // The BoardView depends on the currentPlayer
        // If the Player has white Pieces, flip the board to have the white Pieces at the bottom of the board
        vm.put("board", (currentPlayer == redPlayer ? boardView : boardView.getFlippedBoard()));

        // Render the Game Page
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}

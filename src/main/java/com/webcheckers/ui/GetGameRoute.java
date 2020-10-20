package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.UIProtocol.*;
import static spark.Spark.halt;

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

    private final GameCenter gameCenter;

    /** A model-tier component that houses and represents the checkers game */
    private final BoardView boardView;

    private final Map<String, Object> modeOptionsAsJSON = new HashMap<>(2);;

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
    public GetGameRoute(final TemplateEngine templateEngine, GameCenter gameCenter, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
        boardView = new BoardView();
        LOG.config("GetGameRoute is initialized.");
    }

    public String makeGameID(Player redPlayer, Player whitePlayer) {
        return (redPlayer.getName() + whitePlayer.getName());
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
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        // Gets the current player
        Player currentPlayer = session.attribute(PLAYER_ATTR);
        Player opponent;

        // Gets the opponent
        if (currentPlayer.inGame())
            opponent = currentPlayer.getOpponent();
        else
            opponent = playerLobby.getPlayer(request.queryParams("opponent"));

        if (opponent == null) {
            response.redirect(WebServer.HOME_URL);
            return halt();
        }

        // Logs a FINER invocation message
        LOG.finer("GetGameRoute is invoked.");

        // Creates the HashMap to house all the freemarker components
        Map<String, Object> vm = new HashMap<>();

        // Checks if opponent is not in an existing game
        if (opponent.getOpponent() != null && !opponent.getOpponent().equals(currentPlayer)) {

            // Redirect and inform currentPlayer that their opponent is playing in a different game
            session.attribute(LEGIT_OPPONENT_ATTR, false);
            response.redirect(WebServer.HOME_URL);
            return halt();
        }

        // Start a new game
        if (!currentPlayer.inGame()) {

            // Sets the currentPlayer's state to be MID_GAME
            session.attribute(MID_GAME_ATTR, true);
            session.attribute(LEGIT_OPPONENT_ATTR, true);
            session.attribute(RED_ATTR, currentPlayer);
            session.attribute(WHITE_ATTR, opponent);

            String gameID = makeGameID(session.attribute(RED_ATTR), session.attribute(WHITE_ATTR));
            session.attribute(GAME_ID_ATTR, gameID);
            gameCenter.addGame(new Game(gameID));

            // Set currentPlayer and opponent's states to PLAYING in playerLobby and start calling opponent to the game
            currentPlayer.joinGame();
            opponent.call();

            // Inform PlayerLobby that currentPlayer and opponent are now playing
            playerLobby.setOpponentMatch(currentPlayer, opponent);
            playerLobby.removePlayer(currentPlayer);
            playerLobby.removePlayer(opponent);
        }

        // Check if currentPlayer was called into a game by opponent
        if (currentPlayer.isCalledForGame()) {
            currentPlayer.joinGame();

            // Adds Red and White players to the session
            session.attribute(RED_ATTR, opponent);
            session.attribute(WHITE_ATTR, currentPlayer);
        }

        // The BoardView depends on the currentPlayer
        // If the Player has white Pieces, flip the board to have the white Pieces at the bottom of the board
        if (currentPlayer.equals(session.attribute(RED_ATTR)))
            session.attribute(BOARD_ATTR, boardView);
        else
            session.attribute(BOARD_ATTR, boardView.flipBoard());

        session.attribute(ACTIVE_COLOR_ATTR, activeColor.RED);

        if (opponent.resigned()) {
            modeOptionsAsJSON.put("isGameOver", true);
            modeOptionsAsJSON.put("gameOverMessage", (opponent.getName() + " resigned."));
        }

        // Adds all freemarker components to the HashMap
        vm.put("title", "Game");
        vm.put("currentUser", currentPlayer);
        vm.put("loggedIn", true);
        vm.put("viewMode", viewMode.PLAY);
        vm.put("modeOptionsAsJSON", new Gson().toJson(modeOptionsAsJSON));
        vm.put("redPlayer", session.attribute(RED_ATTR));
        vm.put("whitePlayer", session.attribute(WHITE_ATTR));
        vm.put("activeColor", session.attribute(ACTIVE_COLOR_ATTR));
        vm.put("board", session.attribute(BOARD_ATTR));

        // Render the Game Page
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}

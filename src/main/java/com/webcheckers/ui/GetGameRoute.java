package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
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
 * opponent's name or when a Player is called into a Game by an opponent The checkers game
 * screen (game.ftl) should be visible after the execution (if the opponent has not joined a game already).
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

    /** A site-wide GameCenter used for storing any ongoing games and global statistics */
    private final GameCenter gameCenter;

    /** An enumeration of the mode selected by the user to enter into */
    enum viewMode {
        PLAY,
        SPECTATOR,
        REPLAY
    }

    /**
     * Creates the Spark Route that handles all the 'GET /game' requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine, GameCenter gameCenter, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
        LOG.config("GetGameRoute is initialized.");
    }

    public String makeGameID(Player redPlayer, Player whitePlayer) {
        return (redPlayer.getName() + whitePlayer.getName());
    }

    /**
     * This method is called to handle the 'GET /game' request. Whenever a Player
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
        Map<String, Object> modeOptionsAsJSON = new HashMap<>(2);
        final Session session = request.session();

        // Gets the current player
        Player currentPlayer = session.attribute(PLAYER_ATTR);
        Player opponent;

        // Gets the opponent: from queryParams if CALLED, from Player object if currentPlayer started the Game
        if (currentPlayer.inGame())
            opponent = currentPlayer.getOpponent();
        else
            opponent = playerLobby.getPlayer(request.queryParams("opponent"));

        // Checks if the opponent has not been matched with currentPlayer (should not happen)
        if (opponent == null) {
            response.redirect(WebServer.HOME_URL);
            return halt();
        }

        // Logs a FINER invocation message
        LOG.finer("GetGameRoute is invoked.");

        // Checks if the opponent's opponent matches currentPlayer
        if (opponent.getOpponent() != null && !opponent.getOpponent().equals(currentPlayer)) {

            // Redirect and inform currentPlayer that their opponent is playing in a different game
            session.attribute(LEGIT_OPPONENT_ATTR, false);
            response.redirect(WebServer.HOME_URL);
            return halt();
        }

        // Start a new game
        if (!currentPlayer.inGame()) {
            String gameID = makeGameID(currentPlayer, opponent);
            Game game = new Game(gameID, currentPlayer, opponent);

            // Sets
            session.attribute(LEGIT_OPPONENT_ATTR, true);
            session.attribute(GAME_ID_ATTR, gameID);

            System.out.println("GAME CREATED: " + gameID);

            gameCenter.addGame(game);

            // Set currentPlayer and opponent's states to PLAYING in playerLobby and start calling opponent to the game
            currentPlayer.joinGame(true);
            opponent.call();

            // Inform PlayerLobby that currentPlayer and opponent are now playing
            playerLobby.setOpponentMatch(currentPlayer, opponent);
            playerLobby.removePlayer(currentPlayer);
            playerLobby.removePlayer(opponent);
        }

        // Check if currentPlayer was called into a game by opponent
        if (currentPlayer.isCalledForGame()) {
            currentPlayer.joinGame(false);

            String gameID = makeGameID(opponent, currentPlayer);
            session.attribute(GAME_ID_ATTR, gameID);
        }

        // Checks if the currentPlayer's opponent has prematurely left the game
        if (!opponent.inGame()) {
            modeOptionsAsJSON.put("isGameOver", true);
            modeOptionsAsJSON.put("gameOverMessage", (opponent.getName() + " resigned."));

            currentPlayer.exitGame();
            playerLobby.addPlayer(currentPlayer);
        }

        Game game = gameCenter.getGame(session.attribute(GAME_ID_ATTR));

        // Creates the HashMap to house all the freemarker components
        Map<String, Object> vm = new HashMap<>();

        // Adds all freemarker components to the ViewMarker HashMap
        vm.put("title", "Game");
        vm.put("currentUser", currentPlayer);
        vm.put("loggedIn", currentPlayer.isSignedIn());
        vm.put("viewMode", viewMode.PLAY);
        vm.put("modeOptionsAsJSON", new Gson().toJson(modeOptionsAsJSON));
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getActiveColor());

        vm.put("board", (currentPlayer.equals(game.getRedPlayer()) ? game.getRedView() : game.getWhiteView()));

        if (!currentPlayer.inGame())
            session.attribute(GAME_ID_ATTR, null);

        // Render the Game Page
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}

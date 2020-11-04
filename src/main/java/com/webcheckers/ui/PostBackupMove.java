package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Turn;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;

/**
 * This Route UI components handles all redirects to /backupMove. This is a simple instance
 * when a user clicks on the 'Backup' button in the 'Controls' section of the GUI. PostBackupMove
 * reverts the board back to an Empty State (when the user did not make any valid Moves) by removing the
 * last valid Move.
 *
 * @author Dmitry Selin
 */
public class PostBackupMove implements Route {

    /** A server-wide GameCenter that keeps track all running games */
    private final GameCenter gameCenter;

    /**
     * A constructor for PostBackupMove. Creates a Route for handling all
     * POST /backupMove requests.
     *
     * @param gameCenter server-wide GameCenter object
     */
    public PostBackupMove(GameCenter gameCenter) { this.gameCenter = gameCenter; }

    /**
     * The method that executes upon every POST /backupMove request call. Simply identifies the
     * current Turn this Player is on, removes the last valid Move, and returns an INFO Message when
     * successful or ERROR Message when the Player has made no moves this Turn.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @return an INFO Message object when successfully removed last Move, otherwise, an ERROR Message (in JSON)
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        // Gets the current Game this Player is in and use it to get the current Turn
        Game game = gameCenter.getGame(session.attribute(GAME_ID_ATTR));
        Turn currentTurn = game.getCurrentTurn();

        // If this Player has made a valid Move this Turn - remove it
        if (currentTurn.hasMoves()) {
            game.getActivePlayerBoard().recoverMove(currentTurn.getLastMove());
            currentTurn.removeLastValidMove();

            return new Gson().toJson(Message.info("Removed last valid move"));
        }

        // In the case that the Player has not made any Moves, return an ERROR
        return new Gson().toJson(Message.error("No moves made this turn. Cannot backup"));
    }
}

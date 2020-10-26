package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;

/**
 * Handles the Resign button in the controls.
 *
 * @author Shubhang Mehrotra, Dmitry Selin
 */
public class PostResignGame implements Route {

    private final PlayerLobby playerLobby;

    private final GameCenter gameCenter;

    public PostResignGame(GameCenter gameCenter, PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        session.attribute(GAME_ID_ATTR, null);
        Player currentPlayer = session.attribute(PLAYER_ATTR);
        currentPlayer.exitGame();

        playerLobby.addPlayer(currentPlayer);

        return new Gson().toJson(Message.info(currentPlayer.getName() + " resigned."));
    }
}

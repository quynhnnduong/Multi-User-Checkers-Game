package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;

public class PostCheckTurnRoute implements Route {
    private final GameCenter gameCenter;

    public PostCheckTurnRoute(GameCenter gameCenter) { this.gameCenter = gameCenter; }

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        Player currentPlayer = session.attribute(PLAYER_ATTR);
        Game game = gameCenter.getGame(session.attribute(GAME_ID_ATTR));
        Message message = Message.info("false");

        if (currentPlayer.isMyTurn() || game.hasPlayerResigned())
            message = Message.info("true");


        return new Gson().toJson(message);
    }
}

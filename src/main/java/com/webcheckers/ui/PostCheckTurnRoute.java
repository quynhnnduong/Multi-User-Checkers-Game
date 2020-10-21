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

        Message message = Message.info("false");

        if (currentPlayer.getTurn()) {
            Game game = gameCenter.getGame(session.attribute(GAME_ID_ATTR));
            game.addTurn();

            GetGameRoute.activeColor activeColor = session.attribute(ACTIVE_COLOR_ATTR);
            session.attribute(ACTIVE_COLOR_ATTR, (activeColor == GetGameRoute.activeColor.RED ?
                    GetGameRoute.activeColor.WHITE : GetGameRoute.activeColor.RED));

            message = Message.info("true");
        }

        return new Gson().toJson(message);
    }
}

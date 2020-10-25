package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.*;

public class PostCheckTurnRoute implements Route {

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();

        Player currentPlayer = session.attribute(PLAYER_ATTR);
        Message message = Message.info("false");

        if (currentPlayer.isMyTurn() || currentPlayer.getOpponent().resigned())
            message = Message.info("true");

        return new Gson().toJson(message);
    }
}

package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;

public class PostCheckTurnRoute implements Route {

    @Override
    public Object handle(Request request, Response response) {

        final Session session = request.session();

        Player currentPlayer = session.attribute(PLAYER_ATTR);

        Message message = Message.info("false");

        if ( !currentPlayer.getOpponent().isTurn()){
            message = Message.info("true");
        }

        return new Gson().toJson(message);

    }
}

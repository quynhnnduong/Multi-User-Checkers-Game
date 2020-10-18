package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

import static com.webcheckers.ui.GetGameRoute.RED_ATTR;
import static com.webcheckers.ui.GetGameRoute.WHITE_ATTR;

public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());
    private TemplateEngine templateEngine;

    public PostCheckTurnRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
        LOG.config("PostCheckTurnRoute is initialized");
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {

        final Session session = request.session();

        //TODO actually check if its the waiting player's turn, and add the opponent's name to queryparams
        //for now assume its not the waiting player's turn

        Player currentPlayer = session.attribute(GetHomeRoute.PLAYER_ATTR);

        Message message = Message.info("false");


        // if it not the opponent's turn switch whoever's going
        if ( !currentPlayer.getOpponent().isTurn()){
            message = Message.info("true");
        }


        Gson gson = new Gson();
        String messageJson = gson.toJson(message);

        return messageJson;

    }
}

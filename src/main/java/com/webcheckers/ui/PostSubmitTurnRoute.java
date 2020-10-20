package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.UIProtocol.RED_ATTR;
import static com.webcheckers.ui.UIProtocol.WHITE_ATTR;

/**
 * Route for determining if the turn is valid
 */
public class PostSubmitTurnRoute implements Route {

    private TemplateEngine templateEngine;
    private static final String TURN_ERROR = "If you're seeing this, you did a move you weren't supposed to";
    private static final String TURN_SUBMIT = "Turn submitted";

    public PostSubmitTurnRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        //TODO: Add a way to validate a turn, possibly using boardVie w

        //for now assume its valid
        //add true like this for testing
        session.attribute("lastValidTurn", true);
        boolean isValidTurn = session.attribute("lastValidTurn"); //isTurnValid(boardView)

        Map<String, Object> vm = new HashMap<>();



        //TODO use the move to determine what kind of message to show?
//        String messageText = "If you're seeing this, you did a move you weren't supposed to";

        //for now, assume all moves are valid
        Message message;
        if (isValidTurn){
            message = Message.info(TURN_SUBMIT);
            //add messages to the view model
            vm.put("message", message);

            //get the opponents name
            /**
             * Not useful here, will probably remove
            //Player currentPlayer =  session.attribute(PLAYER_ATTR);
            //String opponentName = currentPlayer.getOpponent().getName();
            //vm.put("activeColor", GetGameRoute.activeColor.WHITE);
            //response.redirect(WebServer.GAME_URL + "?opponent=" + opponentName);
             */

            //change who is playing
            Player redPlayer = session.attribute(RED_ATTR);
            Player whitePlayer = session.attribute(WHITE_ATTR);

            // if it not the opponent's turn switch whoever's going
            if (redPlayer.isTurn()){
                redPlayer.stopTurn();
                whitePlayer.startTurn();
            } else {
                whitePlayer.stopTurn();
                redPlayer.startTurn();
            }

        } else {
            // create a message with an error
            message = Message.error(TURN_ERROR);
            //add messages to the view model
            vm.put("message", message);
        }

        //convert moveJSON to move Object using Gson
        Gson gson = new Gson();
        String messageJson = gson.toJson(message);
        // render the View
        return messageJson;
    }
}

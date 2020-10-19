package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.webcheckers.ui.UIProtocol.*;

/**
 * This class handles the /validateMove route
 * Used for allowing player moves to happen (and later validating if the move was legal)
 * @author Joel Clyne
 */
public class PostValidateMoveRoute implements Route {
    /** A logger used to send messages to the console - for testing purposes */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    /** The Template Engine that generates the '.ftl' pages */
    private final TemplateEngine templateEngine;

    /** A server-wide Player Lobby that keeps track of all the players waiting to start a game */
    private final PlayerLobby playerLobby;

    //TODO Add  functionality and documentation to map
    private final HashMap<String, Object> map = null;

    public PostValidateMoveRoute(TemplateEngine templateEngine, PlayerLobby playerLobby){
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();

        Map<String, Object> vm = new HashMap<>();

        //get the move from actionData (see sprint2 documentation) as JSON string
        String moveJSON = request.queryParams("actionData");

        //convert moveJSON to move Object using Gson
        Gson gson = new Gson();
        Move move = gson.fromJson(moveJSON, Move.class);

        //TODO Implement isValidMove to determine if move was actually valid
        boolean isValidMove = true; //isValidMove(move)

        //TODO use the move to determine what kind of message to show?
        String messageText = "I Don't Know What's Supposed to Go Here";

        //for now, assume all moves are valid
        Message message;
        if (isValidMove){
            message = Message.info(messageText);
        } else {
            // create a message with an error
            message = Message.error(messageText);
        }

        //Get the players
        Player currentPlayer = session.attribute(PLAYER_ATTR);
        Player redPlayer = session.attribute(RED_ATTR);
        Player whitePlayer = session.attribute(WHITE_ATTR);

        //Get the board
        BoardView boardView = session.attribute(BOARD_ATTR);

        //TODO Enable the submit turn button

        //add messages to the view model
        vm.put("message", message);

        //add other parts to the view model
        vm.put("title", "Game");
        vm.put("currentUser", currentPlayer);
        vm.put("loggedIn", true);
        vm.put("viewMode", GetGameRoute.viewMode.PLAY);
        vm.put("modeOptionsAsJSON", map);
        vm.put("redPlayer", redPlayer);
        vm.put("whitePlayer", whitePlayer);
        vm.put("activeColor", GetGameRoute.activeColor.RED);

        // The BoardView depends on the currentPlayer
        // If the Player has white Pieces, flip the board to have the white Pieces at the bottom of the board
        vm.put("board", (currentPlayer == redPlayer ? boardView : boardView.flipBoard()));


        // return the move as a JSON string
        return moveJSON;
        //return templateEngine.render(new ModelAndView(vm , "message.ftl"));
    }
}

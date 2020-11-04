package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;

public class PostReplayNextTurnRoute implements Route {

    /** The Template Engine that generates the '.ftl' pages */
    private final TemplateEngine templateEngine;

    //private final ReplaySaver replaySaver;

    private final ReplayLoader replayLoader;

    public PostReplayNextTurnRoute(TemplateEngine templateEngine, ReplayLoader replayLoader){
        this.templateEngine = templateEngine;
        //this.replaySaver = replaySaver;
        this.replayLoader = replayLoader;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Player currentUser = session.attribute(PLAYER_ATTR);
        currentUser.startSpectating();
        //TODO add logic for getting the turn from the replay loader
        String gameId = request.queryParams("gameID");
        Replay replay = replayLoader.getReplay(gameId);

        //get the next turn of the replay
        replay.incrementTurn();
        //if (replay.getCurrentTurnNum() >= 0){
        //    ReplayMove turn = replay.getCurrentTurn();
        //}
        executeReplayMove(replay);
        System.out.println("Over here " + replay.getCurrentTurnNum());


        /*
        Map<String, Object> modeOptionsAsJSON = new HashMap<>();
        //check if there are moves left
        if (replay.getCurrentTurnNum() > replay.getMaxTurns()){
            modeOptionsAsJSON.put("hasNext", true);
        } else {
            modeOptionsAsJSON.put("hasNext", false);
        }

        if (replay.getCurrentTurnNum() < 0){
            modeOptionsAsJSON.put("hasPrevious", true);
        } else {
            modeOptionsAsJSON.put("hasPrevious", false);
        }
        BoardView redBoard = replay.getGame().getRedView();





        // Creates the HashMap to house all the freemarker components
        Map<String, Object> vm = new HashMap<>();

        // Adds all freemarker components to the ViewMarker HashMap
        vm.put("title", "Game");
        vm.put("currentUser", currentUser);
        vm.put("loggedIn", currentUser.isSignedIn());
        vm.put("viewMode", GetGameRoute.viewMode.REPLAY);
        vm.put("modeOptionsAsJSON", new Gson().toJson(modeOptionsAsJSON));
        vm.put("redPlayer", replay.getRed());
        vm.put("whitePlayer", replay.getWhite());
        vm.put("activeColor", Game.ActiveColor.RED);
        vm.put("board", redBoard);
        */

        return new Gson().toJson(Message.info("true"));
    }

    public void executeReplayMove(Replay replay ){
        ReplayMove replayMove = replay.getCurrentTurn();
        Game.ActiveColor currentColor = replayMove.getPlayerColor();
        Game game = replay.getGame();
        BoardView redView = game.getRedView();
        BoardView whiteView = game.getWhiteView();
        if (currentColor == Game.ActiveColor.RED){
            //do the move on the red view
            redView.makeMove(replayMove.getMove());
            whiteView.makeMove(replayMove.getMove().getFlippedMove());
        } else if (currentColor == Game.ActiveColor.WHITE){
            whiteView.makeMove(replayMove.getMove());
            redView.makeMove(replayMove.getMove().getFlippedMove());
        }
    }
}

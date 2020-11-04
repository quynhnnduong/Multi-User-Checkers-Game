package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;

public class GetReplayGameRoute implements Route {

    /** The Template Engine that generates the '.ftl' pages */
    private final TemplateEngine templateEngine;

    //private final ReplaySaver replaySaver;

    private final ReplayLoader replayLoader;

    public GetReplayGameRoute(TemplateEngine templateEngine, ReplayLoader replayLoader){
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

        Map<String, Object> modeOptionsAsJSON = new HashMap<>();
        BoardView redBoard = replay.getGame().getRedView();
        BoardView whiteBoard = replay.getGame().getWhiteView();


        //get the number of the current turn of the replay
        int turnNum = replay.getCurrentTurnNum();

        if (turnNum == -1){
            //set the mode options to the beginning state
            modeOptionsAsJSON.put("hasNext", true);
            modeOptionsAsJSON.put("hasPrevious", false);
            redBoard.resetBoard();
            whiteBoard.resetBoard();
        } else {
            //check if there are moves left
            if (replay.getCurrentTurnNum() < replay.getMaxTurns()){
                modeOptionsAsJSON.put("hasNext", true);
            } else {
                modeOptionsAsJSON.put("hasNext", false);
            }

            if (replay.getCurrentTurnNum() >= 0){
                modeOptionsAsJSON.put("hasPrevious", true);
            } else {
                modeOptionsAsJSON.put("hasPrevious", false);
            }
        }





        // Creates the HashMap to house all the freemarker components
        Map<String, Object> vm = new HashMap<>();

        //reset the board for both players




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
        //vm.put("message", )

        System.out.println("Reached here");

        // Render the Game Page
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
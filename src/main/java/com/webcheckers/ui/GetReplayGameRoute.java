package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.UIProtocol.*;

/**
 * The class that handles displaying the program for the replay view
 */
public class GetReplayGameRoute implements Route {

    /** The Template Engine that generates the '.ftl' pages */
    private final TemplateEngine templateEngine;

    /** The loader for the replays */
    private final ReplayLoader replayLoader;

    public GetReplayGameRoute(TemplateEngine templateEngine, ReplayLoader replayLoader){
        this.templateEngine = templateEngine;
        this.replayLoader = replayLoader;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Player currentUser = session.attribute(PLAYER_ATTR);
        currentUser.startSpectating();
        String gameId = request.queryParams("gameID");

        //if the user isn't already viewing a replay, make them view the replay of the gameID
        if (session.attribute(REPLAY_COPY) == null){
            Replay sessionReplay = replayLoader.getReplay(gameId);
            session.attribute(REPLAY_COPY, sessionReplay);
        }
        Replay replay = session.attribute(REPLAY_COPY);

        //They're going to use the blank spectating board to watch the replay
        BoardView spectatingBoard = session.attribute(REPLAY_BOARD);

        //get the number of the current turn of the replay
        int turnNum = replay.getCurrentTurnNum();

        Map<String, Object> modeOptionsAsJSON = new HashMap<>();
        if (turnNum == -1){
            //set the mode options to the beginning state
            modeOptionsAsJSON.put("hasNext", true);
            modeOptionsAsJSON.put("hasPrevious", false);
            spectatingBoard.resetBoard();
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

        // Adds all freemarker components to the ViewMarker HashMap
        vm.put("title", "Game");
        vm.put("currentUser", currentUser);
        vm.put("loggedIn", currentUser.isSignedIn());
        vm.put("viewMode", GetGameRoute.viewMode.REPLAY);
        vm.put("modeOptionsAsJSON", new Gson().toJson(modeOptionsAsJSON));
        vm.put("redPlayer", replay.getRed());
        vm.put("whitePlayer", replay.getWhite());
        vm.put("activeColor", Game.ActiveColor.RED);
        vm.put("board", spectatingBoard);

        // Render the Game Page
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
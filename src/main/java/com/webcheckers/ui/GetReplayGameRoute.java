package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.ReplayLoader;
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


        //get the current turn of the replay
        replay.getCurrentTurn();

        //set the mode options to the beginning state
        Map<String, Object> modeOptionsAsJSON = new HashMap<>();
        modeOptionsAsJSON.put("hasNext", true);
        modeOptionsAsJSON.put("hasPrevious", false);

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
        vm.put("board", replay.getGame().getRedView());
        //vm.put("message", )

        System.out.println("Reached here");

        // Render the Game Page
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
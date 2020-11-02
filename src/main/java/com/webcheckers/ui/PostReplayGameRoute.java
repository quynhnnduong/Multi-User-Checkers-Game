package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.ReplayLoader;
import com.webcheckers.model.ReplaySaver;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;

public class PostReplayGameRoute implements Route {

    /** The Template Engine that generates the '.ftl' pages */
    private final TemplateEngine templateEngine;

    private final ReplaySaver replaySaver;

    private final ReplayLoader replayLoader;

    public PostReplayGameRoute(TemplateEngine templateEngine, ReplaySaver replaySaver, ReplayLoader replayLoader){
        this.templateEngine = templateEngine;
        this.replaySaver = replaySaver;
        this.replayLoader = replayLoader;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Player currentUser = session.attribute(PLAYER_ATTR);
        currentUser.startSpectating();
        //TODO add logic for getting the turn from the replay loader

        // Creates the HashMap to house all the freemarker components
        Map<String, Object> vm = new HashMap<>();

        return null;
    }
}

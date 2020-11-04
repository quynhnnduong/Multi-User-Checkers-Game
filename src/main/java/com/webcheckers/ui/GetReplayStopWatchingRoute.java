package com.webcheckers.ui;

import com.webcheckers.model.Player;
import spark.*;

import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;
import static spark.Spark.halt;

public class GetReplayStopWatchingRoute implements Route {

    /** The Template Engine that generates the '.ftl' pages */
    //private final TemplateEngine templateEngine;

    public GetReplayStopWatchingRoute(TemplateEngine templateEngine){

        //this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Player currentUser = session.attribute(PLAYER_ATTR);
        currentUser.stopSpectating();
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}

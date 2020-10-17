package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class PostCheckTurnRoute implements Route {

    private TemplateEngine templateEngine;

    public PostCheckTurnRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {

        //TODO actually check if its the waiting player's turn, and add the opponent's name to queryparams

        //for now assume its not the waiting player's turn
        Message message = Message.info("false");
        Gson gson = new Gson();
        String messageJson = gson.toJson(message);

        return messageJson;

    }
}

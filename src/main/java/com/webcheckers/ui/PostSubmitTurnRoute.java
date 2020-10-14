package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostSubmitTurnRoute implements Route {

    private TemplateEngine templateEngine;

    public PostSubmitTurnRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        Map<String, Object> vm = new HashMap<>();

        // render the View
        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }
}

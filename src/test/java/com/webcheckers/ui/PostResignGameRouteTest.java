package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostResignGameRouteTest {

    private PostResignGame CuT;
    private TemplateEngine engine;
    private Request request;
    private Session session;
    private Response response;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        // create mocks
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        // create a new CuT for each test
        CuT = new PostResignGame(engine);
    }

    /**
     * Check if the resignation is successful
     */
    @Test
    public void resignGame() throws Exception {
        Object resignation = CuT.handle(request, response);
        Gson gson = new Gson();
        assertEquals(gson.toJson(Message.info("Player Resigned")), resignation);
    }


}

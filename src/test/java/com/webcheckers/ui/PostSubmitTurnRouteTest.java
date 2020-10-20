package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *  Unit Test suite for {@link PostSubmitTurnRoute} component
 * @author Quynh Duong
 */
public class PostSubmitTurnRouteTest {


    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private PostSubmitTurnRoute CuT;

    private final String redPlayer = "redPlayer";
    private final String whitePlayer = "whitePlayer";

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);

        CuT = new PostSubmitTurnRoute(engine);
    }

    /**
     * tests to make sure there are no errors when submitting a turn
     */
    @Test
    public void submitTurn() {
        when(request.session().attribute(UIProtocol.RED_ATTR)).thenReturn(redPlayer);
        when(request.session().attribute(UIProtocol.WHITE_ATTR)).thenReturn(whitePlayer);
        try {
            Object turn_submit = CuT.handle(request, response);
            assertEquals("{\"type\":\"info\",\"text\":\"Turn submitted\"}", turn_submit);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * test to make sure an error message is sent when appropriate
     */
    @Test
    void errorMessage() {
        when(request.session().attribute(UIProtocol.RED_ATTR)).thenReturn(redPlayer);
        when(request.session().attribute(UIProtocol.WHITE_ATTR)).thenReturn(whitePlayer);
        try {
            Object error = CuT.handle(request, response);
            assertEquals("{\"type\":\"error\",\"text\":\"If you're seeing this, you did a move you weren't supposed to\"}", error);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

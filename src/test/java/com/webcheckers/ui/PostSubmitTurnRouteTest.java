package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
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
    private GameCenter gameCenter;

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
        gameCenter = new GameCenter();

        CuT = new PostSubmitTurnRoute(gameCenter);
    }

    /**
     * tests to make sure there are no errors when submitting a turn
     */
   @Test
    public void submitTurn() throws Exception {
        //This part works fine
        Gson gson = new Gson();
        when(request.session().attribute(UIProtocol.RED_ATTR)).thenReturn(new Player("redPlayer"));
        when(request.session().attribute(UIProtocol.WHITE_ATTR)).thenReturn(new Player("whitePlayer"));
        when(session.attribute("lastValidTurn")).thenReturn(true);
        Object submit = CuT.handle(request, response);

        try {
            assertEquals(gson.toJson(Message.info("Successfully Submitted Turn")), submit);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * test to make sure an error message is sent when appropriate
     */
    @Test
    void errorMessage() throws Exception {
        //This part works fine
        Gson gson = new Gson();
        when(request.session().attribute(UIProtocol.RED_ATTR)).thenReturn(new Player("redPlayer"));
        when(request.session().attribute(UIProtocol.WHITE_ATTR)).thenReturn(new Player("whitePlayer"));
        when(session.attribute("lastValidTurn")).thenReturn(false);
        Object error = CuT.handle(request, response);
        assertEquals(gson.toJson(Message.error("Turn cannot be submitted")), error);
    }
}

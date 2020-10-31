package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spark.*;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;
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
//   @Test
    public void submitTurn() throws Exception {
       Gson gson = new Gson();
       when(session.attribute(GAME_ID_ATTR)).thenReturn("1234");
       Game game = gameCenter.getGame(session.attribute(GAME_ID_ATTR));
       when(game.getCurrentTurn().hasMoves()).thenReturn(true);
//       when(gameCenter.getGame("1234")).thenReturn(game);
       Object submit = CuT.handle(request, response);
       assertEquals(gson.toJson(Message.info("Successfully Submitted Turn")), submit);
    }

    /**
     * test to make sure an error message is sent when appropriate
     */
    @Test
    void errorMessage() throws Exception {
        //This part works fine
        Gson gson = new Gson();
        when(session.attribute(GAME_ID_ATTR)).thenReturn("1234");
        Object error = CuT.handle(request, response);
        assertEquals(gson.toJson(Message.error("Turn cannot be submitted")), error);
    }

    /**
     * test to make sure an error message is sent when there's null game
     */
    @Test
    void nullGame(){
        Gson gson = new Gson();
        when(session.attribute(GAME_ID_ATTR)).thenReturn(null);
        Object error = CuT.handle(request, response);
        assertEquals(gson.toJson(Message.error("Turn cannot be submitted")), error);
    }
}

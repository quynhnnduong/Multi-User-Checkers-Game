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

import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PostCheckTurnRouteTest {

    // Attributes holding mock objects
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player currentPlayer;
    private Player opponent;
    private PostCheckTurnRoute CuT;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        opponent = mock(Player.class);
        currentPlayer = mock(Player.class);

        //friendly
        currentPlayer = new Player("user");
        opponent = new Player("opponent");
        currentPlayer.setOpponent(opponent);

        // create a unique CuT for each test
        CuT = new PostCheckTurnRoute();
    }

    /**
     * Test that the Game view will create a new game when a player initiates a game
     */
    @Test
    public void keepWaiting() {
        opponent.startTurn();
        when(session.attribute(PLAYER_ATTR)).thenReturn(currentPlayer);
        Gson gson = new Gson();
        Object wait = CuT.handle(request, response);
        assertEquals(gson.toJson(Message.info("false")), wait);
    }

    @Test
    public void testChangeTurn1() {
        Gson gson = new Gson();
        when(session.attribute(PLAYER_ATTR)).thenReturn(currentPlayer);
        currentPlayer = session.attribute(PLAYER_ATTR);

        currentPlayer.startTurn();
        opponent = null;

        Object wait = CuT.handle(request, response);
        assertEquals(gson.toJson(Message.info("true")), wait);
    }

    @Test
    public void testChangeTurn2() {
        Gson gson = new Gson();
        when(session.attribute(PLAYER_ATTR)).thenReturn(currentPlayer);
        currentPlayer = session.attribute(PLAYER_ATTR);

        currentPlayer.startTurn();
        opponent.endTurn();

        Object wait = CuT.handle(request, response);
        assertEquals(gson.toJson(Message.info("true")), wait);
    }

    @Test
    public void testChangeTurn3() {
        // TODO Review this test -Sasha
        Gson gson = new Gson();
        when(session.attribute(PLAYER_ATTR)).thenReturn(currentPlayer);
        currentPlayer = session.attribute(PLAYER_ATTR);

        currentPlayer.endTurn();
        opponent.endTurn();

        Object wait = CuT.handle(request, response);
        assertEquals(gson.toJson(Message.info("false")), wait);
    }

    @Test
    public void testChangeTurn4() {
        // TODO Review this test -Sasha
        Gson gson = new Gson();
        when(session.attribute(PLAYER_ATTR)).thenReturn(currentPlayer);
        currentPlayer = session.attribute(PLAYER_ATTR);

        currentPlayer = null;
        opponent.endTurn();

        Object wait = CuT.handle(request, response);
        assertEquals(gson.toJson(Message.info("false")), wait);
    }


}

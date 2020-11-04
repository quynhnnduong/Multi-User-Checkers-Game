package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;
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
    private PostCheckTurnRoute CuT;
    private Gson gson;
    private GameCenter gameCenter;
    private Game game;
    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        // Set up mocks
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        currentPlayer = mock(Player.class);
        gameCenter = mock(GameCenter.class);
        game = mock(Game.class);

        // Mock methods
        when(request.session()).thenReturn(session);
        when(session.attribute(GAME_ID_ATTR)).thenReturn("gameID");
        when(session.attribute(PLAYER_ATTR)).thenReturn(currentPlayer);

        // Gson object for ...
        gson = new Gson();

        // create a unique CuT for each test
        CuT = new PostCheckTurnRoute(gameCenter);
    }

    /**
     * Test that the route changes the turn to be the user's turn
     * if their turn has been started and if their opponent is null.
     * (True/True branch of conditional in PostCheckTurnRoute, line 19)
     */
    @Test
    public void testChangeTurn1() {
        // Set up
        when(currentPlayer.isMyTurn()).thenReturn(true);
        when(currentPlayer.getOpponent()).thenReturn(null);

        // Invoke
        Object wait = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.info("true")), wait);
    }

    /**
     * Test that the route changes the turn to be the user's turn
     * if it their turn has been started and their opponent is not null.
     * (True/False branch of conditional in PostCheckTurnRoute, line 19)
     */
    @Test
    public void testChangeTurn2() {
        // Set up
        when(currentPlayer.isMyTurn()).thenReturn(true);
        when(currentPlayer.getOpponent()).thenReturn(new Player("opponent"));

        // Invoke
        Object wait = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.info("true")), wait);
    }

    /**
     * Test that the route changes the turn to be the user's turn
     * if their turn has been ended or their opponent is null.
     * (False/True branch of conditional in PostCheckTurnRoute, line 19)
     */
    @Test
    public void testChangeTurn3() {
        // Set up
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR))).thenReturn(game);
        when(currentPlayer.isMyTurn()).thenReturn(false);
        when(currentPlayer.getOpponent()).thenReturn(null);
        when(game.hasPlayerResigned()).thenReturn(true);

        // Invoke
        Object wait = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.info("true")), wait);
    }

    /**
     * Test that the route does not change the turn to be the user's turn
     * if their turn has been ended and their opponent is not null.
     * (False/False branch of conditional in PostCheckTurnRoute, line 19)
     */
    @Test
    public void testChangeTurn4() {
        // Set up
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR))).thenReturn(game);
        when(currentPlayer.isMyTurn()).thenReturn(false);
        when(currentPlayer.getOpponent()).thenReturn(null);
        when(game.hasPlayerResigned()).thenReturn(false);

        // Invoke
        Object wait = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.info("false")), wait);
    }

}

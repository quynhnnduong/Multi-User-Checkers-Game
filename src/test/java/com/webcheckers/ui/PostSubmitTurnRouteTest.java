package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Turn;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spark.*;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;
import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *  Unit Test suite for {@link PostSubmitTurnRoute} component
 * @author Quynh Duong, Sasha Persaud
 */
public class PostSubmitTurnRouteTest {


    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private PostSubmitTurnRoute CuT;
    private GameCenter gameCenter;
    private Game game;
    private Gson gson;
    private Turn turn;


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
        gameCenter = mock(GameCenter.class);
        game = mock(Game.class);
        turn = mock(Turn.class);

        // Mock methods
        when(request.session()).thenReturn(session);

        // Gson object for ...
        gson = new Gson();

        // create a unique CuT for each test
        CuT = new PostSubmitTurnRoute(gameCenter);
    }


    /**
     * Test that a turn is successfully submitted if the current game exists
     * and if moves have been made during this turn.
     */
   @Test
    public void successfulSubmitTurn() {
       // Set up

       // The current game is not null
       when(gameCenter.getGame(session.attribute(GAME_ID_ATTR))).thenReturn(game);
       // There have been moves made
       when(game.getCurrentTurn()).thenReturn(turn);
       when(game.getCurrentTurn().hasMoves()).thenReturn(true);

       // Invoke
       Object submit = CuT.handle(request, response);

       // Analyze
       assertEquals(gson.toJson(Message.info("Successfully Submitted Turn")), submit);
    }

    /**
     * Test that a turn is not submitted if the current game is null
     * and if moves have been made during this turn.
     */
    @Test
    public void invalidSubmitTurn1() {
        // Set up

        // The current game is null
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR))).thenReturn(null);
        // There have been moves made
        when(game.getCurrentTurn()).thenReturn(turn);
        when(game.getCurrentTurn().hasMoves()).thenReturn(true);

        // Invoke
        Object submit = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("Turn cannot be submitted")), submit);
    }

    /**
     * Test that a turn is not submitted if the current game exists
     * and if no moves have been made during this turn.
     */
    @Test
    public void invalidSubmitTurn2() {
        // Set up

        // The current game is not null
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR))).thenReturn(game);
        // There have been no moves made
        when(game.getCurrentTurn()).thenReturn(turn);
        when(game.getCurrentTurn().hasMoves()).thenReturn(false);

        // Invoke
        Object submit = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("Turn cannot be submitted")), submit);
    }

    /**
     * Test that a turn is not submitted if the current game does not exist
     * and if no moves have been made during this turn.
     */
    @Test
    public void invalidSubmitTurn3() {
        // Set up

        // The current game is null
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR))).thenReturn(null);
        // There have been no moves made
        when(game.getCurrentTurn()).thenReturn(turn);
        when(game.getCurrentTurn().hasMoves()).thenReturn(false);

        // Invoke
        Object submit = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("Turn cannot be submitted")), submit);
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

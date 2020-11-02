package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Position;
import com.webcheckers.model.Turn;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostValidateMoveRouteTest {

    // Attributes holding mock objects
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Game game;
    private Turn turn;
    private Gson gson;

    private Position start, end;
    private int rowDiff, colDiff;
    private BoardView redView, whiteView, curView;
    private Game.ActiveColor curColor;

    // Component under test (CuT)
    private PostValidateMoveRoute CuT;

    //friendlies
    private boolean isValidMove;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        game = mock(Game.class);
        turn = mock(Turn.class);
        start = mock(Position.class);
        end = mock(Position.class);
        gson = new Gson();

        when(request.session()).thenReturn(session);

        CuT = new PostValidateMoveRoute(gameCenter);
    }

    @Test
    public void testIfTurnHasMoves() {
//        String messageText = "{\"sampleMove\": { \"row\": 1,\"col\": 1}}";
//        when(request.queryParams("actionData")).thenReturn(messageText);
//        final TemplateEngineTester testHelper = new TemplateEngineTester();
//        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
//        Object validMessage = CuT.handle(request, response);
//        assertEquals(messageText, validMessage);

        //  Set up
        String messageText = "{\"sampleMove\": { \"row\": 1,\"col\": 1}}";
        when(request.queryParams("actionData")).thenReturn(messageText);

        // TODO mock a move (line 32)

        // mock the turn (line 33)
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);
        when(turn.hasMoves()).thenReturn(true);

        // mock the start and end position (lines 34-35) - needed for compile not test
        when(move.getStart()).thenReturn(start);
        when(move.getEnd()).thenReturn(end);

        // TODO hardcode the row/col differences - needed for compile not test

        // TODO mock the board views - needed for compile not test

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Can only move once")), validMessage);

    }
}

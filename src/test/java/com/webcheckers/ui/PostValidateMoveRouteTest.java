package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import java.lang.reflect.Type;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    private Move move;

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
        move = mock(Move.class);
        rowDiff = 0;
        colDiff = 0;
        redView = mock(BoardView.class);
        whiteView = mock(BoardView.class);
        curView = mock(BoardView.class);
        // curColor = Game.ActiveColor.RED;

        when(request.session()).thenReturn(session);

        CuT = new PostValidateMoveRoute(gameCenter);
    }

    // @Test
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

        // mock a move (line 32)
        when(new Gson()).thenReturn(gson); // wrong
        when(new Gson().fromJson(messageText, Move.class)).thenReturn(move);

        // mock the turn (line 33)
        when(gameCenter.getGame(session.attribute(UIProtocol.GAME_ID_ATTR))).thenReturn(game);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getCurrentTurn()).thenReturn(turn);
        when(turn.hasMoves()).thenReturn(true);

        // mock the start and end position (lines 34-35) - needed for compile not test
        when(move.getStart()).thenReturn(start);
        when(move.getEnd()).thenReturn(end);

        // hardcode the row/col differences - needed for compile not test
        when( Math.abs(end.getCell() - start.getCell())).thenReturn(rowDiff);
        when(start.getRow() - end.getRow()).thenReturn(colDiff);

        // mock the board views - needed for compile not test
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getRedView()).thenReturn(redView);
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getWhiteView()).thenReturn(whiteView);

        // hardcode the current color - needed for compile not test
        curColor = Game.ActiveColor.RED;
        when(gameCenter.getGame(session.attribute(GAME_ID_ATTR)).getActiveColor()).thenReturn(curColor);

        // Invoke
        Object validMessage = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.error("INVALID MOVE: Can only move once")), validMessage);

    }
}

package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static com.webcheckers.ui.UIProtocol.REPLAY_WHITE_VIEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Unit Test suite for {@link PostReplayPrevTurnRoute} component
 * @author Quynh Duong
 */
@Tag("UI-tier")
public class PostReplayPrevTurnRouteTest {
    private PostReplayPrevTurnRoute CuT;
    private TemplateEngine engine;
    private Request request;
    private Response response;
    private Session session;
    private ReplayLoader replayLoader;
    private Replay replay;
    private ReplayMove lastMove;
    private String gameId;
    private Player currentPlayer;
    private Game game;
    private BoardView redView;
    private BoardView whiteView;
    private BoardView spectatorView;
    private BoardView fakeWhite;
    private Move move, undoMove;
    private Space space;
    private Gson gson;


    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        // Set up mocks
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        replayLoader = mock(ReplayLoader.class);
        replay = mock(Replay.class);
        lastMove = mock(ReplayMove.class);
        game = mock(Game.class);
        redView = mock(BoardView.class);
        whiteView = mock(BoardView.class);
        spectatorView = mock(BoardView.class);
        fakeWhite = mock(BoardView.class);
        move = mock(Move.class);
        undoMove = mock(Move.class);
        space = mock(Space.class);

        gameId = "1234";
        currentPlayer = mock(Player.class);
        gson = new Gson();

        CuT = new PostReplayPrevTurnRoute(engine, replayLoader);
    }

    @Test
    public void testHandle() {
        // Set up
        when(request.queryParams("gameID")).thenReturn(gameId);
        when(session.attribute(UIProtocol.REPLAY_COPY)).thenReturn(replay);

        when(replay.getCurrentTurn()).thenReturn(lastMove);
        when(replay.getGame()).thenReturn(game);
        when(session.attribute(UIProtocol.REPLAY_BOARD)).thenReturn(spectatorView);
        when(session.attribute(REPLAY_WHITE_VIEW)).thenReturn(fakeWhite);

        when(lastMove.getMove()).thenReturn(move);
        when(move.getUndoMove()).thenReturn(undoMove);
        when(lastMove.getPlayerColor()).thenReturn(Game.ActiveColor.RED);

        // test if it is a jump
        when(undoMove.doesMoveSkipOverSpace()).thenReturn(true);

        // test if the color is red
        when(undoMove.getSpaceInMiddle(spectatorView)).thenReturn(space);
        doNothing().when(space).placePiece(any(Piece.class));

        doNothing().when(replay).decrementTurn();

        // Invoke
        Object result = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.info("true")), result);

    }

    @Test
    public void testHandle2() {
        // Set up
        when(request.queryParams("gameID")).thenReturn(gameId);
        when(session.attribute(UIProtocol.REPLAY_COPY)).thenReturn(replay);

        when(replay.getCurrentTurn()).thenReturn(lastMove);
        when(replay.getGame()).thenReturn(game);
        when(session.attribute(UIProtocol.REPLAY_BOARD)).thenReturn(spectatorView);
        when(session.attribute(REPLAY_WHITE_VIEW)).thenReturn(fakeWhite);

        when(lastMove.getMove()).thenReturn(move);
        when(move.getUndoMove()).thenReturn(undoMove);
        when(lastMove.getPlayerColor()).thenReturn(Game.ActiveColor.WHITE);

        // test if it is a jump
        when(undoMove.doesMoveSkipOverSpace()).thenReturn(true);

        // test if the color is white
        when(undoMove.getSpaceInMiddle(fakeWhite)).thenReturn(space);
        doNothing().when(space).placePiece(any(Piece.class));

        doNothing().when(replay).decrementTurn();

        // Invoke
        Object result = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.info("true")), result);

    }

    @Test
    public void testHandle3() {
        // Set up
        when(request.queryParams("gameID")).thenReturn(gameId);
        when(session.attribute(UIProtocol.REPLAY_COPY)).thenReturn(replay);

        when(replay.getCurrentTurn()).thenReturn(lastMove);
        when(replay.getGame()).thenReturn(game);
        when(session.attribute(UIProtocol.REPLAY_BOARD)).thenReturn(spectatorView);
        when(session.attribute(REPLAY_WHITE_VIEW)).thenReturn(fakeWhite);

        when(lastMove.getMove()).thenReturn(move);
        when(move.getUndoMove()).thenReturn(undoMove);
        when(lastMove.getPlayerColor()).thenReturn(Game.ActiveColor.RED);

        // test if it is not a jump
        when(undoMove.doesMoveSkipOverSpace()).thenReturn(false);

        doNothing().when(replay).decrementTurn();

        // Invoke
        Object result = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.info("true")), result);

    }

}
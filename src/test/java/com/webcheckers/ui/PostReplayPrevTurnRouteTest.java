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
    private ReplayMove replayMove;
    private String gameId;
    private Player currentPlayer;
    private Game game;
    private BoardView redView;
    private BoardView whiteView;
    private BoardView spectatorView;
    private BoardView fakeWhite;
    private Move move;
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
        replayMove = mock(ReplayMove.class);
        game = mock(Game.class);
        redView = mock(BoardView.class);
        whiteView = mock(BoardView.class);
        spectatorView = mock(BoardView.class);
        fakeWhite = mock(BoardView.class);
        move = mock(Move.class);

        gameId = "1234";
        currentPlayer = mock(Player.class);
        gson = new Gson();

        CuT = new PostReplayPrevTurnRoute(engine, replayLoader);
    }

//    @Test
    public void prevReplayTurn() throws Exception {
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(currentPlayer);
        when(request.queryParams("1234")).thenReturn(gameId);
        when(session.attribute(UIProtocol.REPLAY_COPY)).thenReturn(replay);
        when(replay.getCurrentTurn()).thenReturn(replayMove);
        when(replay.getGame()).thenReturn(game);
        when(game.getRedView()).thenReturn(redView);
        when(game.getWhiteView()).thenReturn(whiteView);
        when(session.attribute(UIProtocol.REPLAY_BOARD)).thenReturn(spectatorView);
        when(session.attribute(REPLAY_WHITE_VIEW)).thenReturn(fakeWhite);
        Move undo = replayMove.getMove().getUndoMove();
        doNothing().when(replay).executeReplayMove(Game.ActiveColor.RED, undo, spectatorView, fakeWhite);
        when(move.doesMoveSkipOverSpace()).thenReturn(false);
        doNothing().when(replay).decrementTurn();

//        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));

    }
    @Test
    public void jumpMove() throws Exception {
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(currentPlayer);
        when(request.queryParams("1234")).thenReturn(gameId);
        when(session.attribute(UIProtocol.REPLAY_COPY)).thenReturn(replay);
        when(replay.getCurrentTurn()).thenReturn(replayMove);
        when(replay.getGame()).thenReturn(game);
        when(game.getRedView()).thenReturn(redView);
        when(game.getWhiteView()).thenReturn(whiteView);
        when(session.attribute(UIProtocol.REPLAY_BOARD)).thenReturn(spectatorView);
        when(session.attribute(REPLAY_WHITE_VIEW)).thenReturn(fakeWhite);
        when(move.doesMoveSkipOverSpace()).thenReturn(true);
        when(replayMove.getPlayerColor()).thenReturn(Game.ActiveColor.RED);

//        assertNull(CuT.handle(request, response));


    }
}

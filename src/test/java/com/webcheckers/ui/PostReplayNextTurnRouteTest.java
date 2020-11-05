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

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Unit Test suite for {@link PostReplayNextTurnRoute} component
 * @author Quynh Duong
 */
@Tag("UI-tier")
public class PostReplayNextTurnRouteTest {
    private PostReplayNextTurnRoute CuT;
    private TemplateEngine engine;
    private Request request;
    private Response response;
    private Session session;
    private ReplayLoader replayLoader;
    private Replay replay;
    private ReplayMove replayMove;
    private String gameId;
    private Gson gson;


    private Player currentPlayer;

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
        currentPlayer = mock(Player.class);
        replayLoader = mock(ReplayLoader.class);
        replay = mock(Replay.class);
        replayMove = mock(ReplayMove.class);

        gameId = "1234";
        gson = new Gson();

        CuT = new PostReplayNextTurnRoute(engine, replayLoader);
    }

    @Test
    public void currentReplayTurn() throws Exception {
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(currentPlayer);
        when(request.queryParams("1234")).thenReturn(gameId);
        when(session.attribute(UIProtocol.REPLAY_COPY)).thenReturn(replay);
        doNothing().when(replay).incrementTurn();
        when(replay.getCurrentTurnNum()).thenReturn(1);
        when(replay.getCurrentTurn()).thenReturn(replayMove);

        assertEquals(replay.getCurrentTurn(), replayMove);
        CuT.handle(request, response);
    }

    @Test
    public void newReplayTurn() throws Exception {
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(currentPlayer);
        when(request.queryParams("1234")).thenReturn(gameId);
        when(session.attribute(UIProtocol.REPLAY_COPY)).thenReturn(replay);
        doNothing().when(replay).incrementTurn();
        when(replay.getCurrentTurnNum()).thenReturn(-1);

        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));
    }
}

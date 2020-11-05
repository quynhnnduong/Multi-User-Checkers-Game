package com.webcheckers.ui;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static spark.Spark.halt;

/**
 * Unit Test suite for {@link GetReplayStopWatchingRoute} component
 * @author Quynh Duong
 */
@Tag("UI-tier")
public class GetReplayStopWatchingRouteTest {
    private GetReplayStopWatchingRoute CuT;
    private TemplateEngine engine;
    private Request request;
    private Response response;
    private Session session;
    private BoardView sessionBoard;
    private BoardView fakeBoard;

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
        sessionBoard = mock(BoardView.class);
        fakeBoard = mock(BoardView.class);
        currentPlayer = mock(Player.class);

        CuT = new GetReplayStopWatchingRoute(engine);
    }

    @Test
    public void testReplayMode() throws Exception {
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(currentPlayer);
        when(session.attribute(UIProtocol.REPLAY_COPY)).thenReturn(null);
        when(session.attribute(UIProtocol.REPLAY_BOARD)).thenReturn(sessionBoard);
        doNothing().when(sessionBoard).resetBoard();
        when(session.attribute(UIProtocol.REPLAY_WHITE_VIEW)).thenReturn(fakeBoard);
        doNothing().when(fakeBoard).resetBoard();
        doNothing().when(currentPlayer).stopSpectating();
        assertThrows(HaltException.class, () -> CuT.handle(request, response));
    }
}

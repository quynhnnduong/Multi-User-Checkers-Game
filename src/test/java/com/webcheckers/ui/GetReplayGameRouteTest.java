package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test suite for {@link GetReplayGameRoute} component
 * @author Quynh Duong
 */
@Tag("UI-tier")
public class GetReplayGameRouteTest {
    private GetReplayGameRoute CuT;
    private TemplateEngine engine;
    private Request request;
    private Response response;
    private Session session;
    private ReplayLoader replayLoader;
    private Replay replay;
    private BoardView spectatingBoard;
    private String gameId;
    private Map<String, Object> modeOptionsAsJSON;
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
        spectatingBoard =  mock(BoardView.class);

        gameId = "1234";
        modeOptionsAsJSON = new HashMap<>();

        CuT = new GetReplayGameRoute(engine, replayLoader);
    }

    @Test
    public void newReplayGame() throws Exception {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(currentPlayer);
        when(request.queryParams("1234")).thenReturn(gameId);
        when(session.attribute(UIProtocol.REPLAY_COPY)).thenReturn(replay);
        when(session.attribute(UIProtocol.REPLAY_BOARD)).thenReturn(spectatingBoard);
        when(replay.getCurrentTurnNum()).thenReturn(0);

        CuT.handle(request, response);

        // Analyze
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Game");
        testHelper.assertViewModelAttribute("loggedIn", currentPlayer.isSignedIn());
        testHelper.assertViewModelAttribute("viewMode", GetGameRoute.viewMode.REPLAY);
        testHelper.assertViewModelAttribute("redPlayer", replay.getRed());
        testHelper.assertViewModelAttribute("whitePlayer", replay.getWhite());
        testHelper.assertViewModelAttribute("activeColor", Game.ActiveColor.RED);
        testHelper.assertViewModelAttribute("board", spectatingBoard);
    }

//    @Test
    public void replayGameBeginningState() throws Exception {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(currentPlayer);
        when(request.queryParams("1234")).thenReturn(gameId);
        when(session.attribute(UIProtocol.REPLAY_COPY)).thenReturn(replay);
        when(session.attribute(UIProtocol.REPLAY_BOARD)).thenReturn(spectatingBoard);
        when(replay.getCurrentTurnNum()).thenReturn(-1);

        CuT.handle(request, response);

        // Analyze
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("hasNext", true);
        testHelper.assertViewModelAttribute("hasPrevious", false);
        doNothing().when(spectatingBoard).resetBoard();
    }

}

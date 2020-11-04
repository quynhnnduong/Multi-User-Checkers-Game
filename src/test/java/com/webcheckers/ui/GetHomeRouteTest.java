package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

import static com.webcheckers.ui.UIProtocol.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


/**
 * Unit Test suite for {@link GetHomeRoute} component
 * @author Shubhang Mehrotra
 */
@Tag("UI-Tier")
public class GetHomeRouteTest {
    /**
     * Component under test: GetHomeRoute Class. (UI-Tier)
     */
    private GetHomeRoute CuT;

    //friendlies
    private GameCenter gameCenter;

    //mocks
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player, opponent;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        player = mock(Player.class);
        opponent = mock(Player.class);
        gameCenter = new GameCenter();

        when(request.session()).thenReturn(session);
        when(session.attribute(PLAYER_ATTR)).thenReturn(player);

        CuT = new GetHomeRoute(engine, gameCenter, playerLobby);
    }

    @Test
    public void testNewSession() {
        // Set up
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(session.attribute(PLAYER_ATTR)).thenReturn(null);
        when(session.attribute(LEGIT_NAME_ATTR)).thenReturn(true);

        // Invoke
        CuT.handle(request, response);

        // Analyze
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("message", Message.info("Welcome to the world of online Checkers."));
        testHelper.assertViewModelAttribute("loggedIn", false);

        assertEquals(true, session.attribute(LEGIT_NAME_ATTR));
    }

    @Test
    public void testMidSession1() throws HaltException {
        // Set up
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(session.attribute(PLAYER_ATTR)).thenReturn(player);
        when(player.inGame()).thenReturn(true);
        when(player.getOpponent()).thenReturn(opponent);
        when(opponent.getName()).thenReturn("opponent");
        doNothing().when(response).redirect(anyString());

        // Invoke
        CuT.handle(request, response);

    }

    @Test
    public void testMidSession2() {
        // Set up
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(session.attribute(PLAYER_ATTR)).thenReturn(player);
        when(player.inGame()).thenReturn(false);
        when(session.attribute(GAME_ID_ATTR)).thenReturn("gameID");

        // Invoke
        CuT.handle(request, response);

    }

    @Test
    public void testMidSession3() {
        // Set up
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(session.attribute(PLAYER_ATTR)).thenReturn(player);
        when(player.inGame()).thenReturn(false);
        when(session.attribute(GAME_ID_ATTR)).thenReturn(null);

        // Invoke
        CuT.handle(request, response);
        

    }

}

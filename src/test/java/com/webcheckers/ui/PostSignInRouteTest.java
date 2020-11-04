package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static spark.Spark.halt;


/**
 * Unit Test suite for {@link GetHomeRoute} component
 * @author Sasha Persaud
 */
@Tag("UI-Tier")
public class PostSignInRouteTest {

    // Component under test (CuT)
    private PostSignInRoute CuT;

    // Friendlies (already tested, no bugs will come from this)
    private GameCenter gameCenter;

    // Attributes holding mock objects
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        // create mocks
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        player = mock(Player.class);
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);

        // create a new CuT for each test
        CuT = new PostSignInRoute(engine, gameCenter, playerLobby);
    }

    /**
     * Test that CuT shows the SignIn view when the session is brand new.
     */
    @Test
    public void testAddNameToLobby() throws Exception {
        // Setup
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(request.queryParams("text_field")).thenReturn("Player1");
        when(playerLobby.addPlayer(any(Player.class))).thenReturn(true);
        when(playerLobby.getLobbyMessage()).thenReturn("There are no other players available to play at this time.");
        when(session.attribute(UIProtocol.LEGIT_NAME_ATTR)).thenReturn(true);

        // Invoke
        CuT.handle(request, response);

        // Analyze
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("message", Message.info("Welcome to the world of online Checkers."));
        // testHelper.assertViewModelAttribute("currentUser", );

        // TODO:  addPlayer takes a string for name and returns bool
        // TODO: use a when/thenReturn for getPlayer, pass in mocked player object

        // testHelper.assertViewModelAttribute("playerList", );
        testHelper.assertViewModelAttribute("loggedIn", true);
        testHelper.assertViewModelAttribute("playersMessage", "There are no other players available to play at this time.");
        testHelper.assertViewModelAttribute(UIProtocol.LEGIT_OPPONENT_ATTR, true);
        testHelper.assertViewName("home.ftl");
    }


    /**
     * Test that CuT shows the SignIn view when the session is brand new.
     */
    // @Test
    public void testInvalidSignIn() {
        // Setup
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(request.queryParams("text_field")).thenReturn("");
        when(playerLobby.addPlayer(any(Player.class))).thenReturn(false);
        when(session.attribute(UIProtocol.LEGIT_NAME_ATTR)).thenReturn(false);

        // Invoke
        CuT.handle(request, response);

        // Analyze
        try {
            CuT.handle(request, response);
        } catch (Exception e){
            assertTrue(e instanceof HaltException);
        }


    }




}

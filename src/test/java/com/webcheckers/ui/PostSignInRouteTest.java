package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

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


        // Invoke
        CuT.handle(request, response);

        // Analyze
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");

        // TODO: check for a shallow comparison assert in TemplateEngineTester (or just get rid of it)

        testHelper.assertViewModelAttribute("message", Message.info("Welcome to the world of online Checkers."));
        // testHelper.assertViewModelAttribute("currentUser", );

        // TODO:  addPlayer takes a string for name and returns bool
        // TODO: use a when/thenReturn for getPlayer, pass in mocked player object

        // testHelper.assertViewModelAttribute("playerList", );
        testHelper.assertViewModelAttribute("loggedIn", true);
        testHelper.assertViewModelAttribute("playersMessage", "There are no other players available to play at this time.");
        testHelper.assertViewModelAttribute(UIProtocol.LEGIT_NAME_ATTR, true);





//        // Set up the test
//        final TemplateEngineTester testHelper = new TemplateEngineTester();
//        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
//        playerLobby.addPlayer(player);
//        session.attribute("playerlobby", playerLobby);
//
//        session.attribute("session_key", UIProtocol.PLAYERLOBBY_ATTR);
//        //when(halt()).thenReturn();
//
//        // Invoke the test: a user signs in
//        System.out.println("Invoking test...");
//
//        try {
//            CuT.handle(request, response);
//        } catch (HaltException he){ // generic exception
//            System.out.println("The program did a redirect"); // assert that it is a halt exception
//        }
//
//        // Analyze the results:
//        //   * model is a non-null Map
//        testHelper.assertViewModelExists();
//        testHelper.assertViewModelIsaMap();
//
//        //   * model contains all necessary View-Model data
//        testHelper.assertViewModelAttribute("title", "Welcome!");
//        testHelper.assertViewModelAttribute(UIProtocol.PLAYER_ATTR, playerLobby.getPlayer(""));
//        //   * test view name
//        testHelper.assertViewName("home.ftl");
//
//        //   * verify the session
//        //verify(session).attribute(eq(UIProtocol.PLAYERLOBBY_ATTR));

    }



}

package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


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

        // create friendlies
        gameCenter = new GameCenter();
        playerLobby = new PlayerLobby(gameCenter);

        player = mock(Player.class);

        // create a new CuT for each test
        CuT = new PostSignInRoute(engine, gameCenter, playerLobby);
    }

    /**
     * Test that CuT shows the SignIn view when the session is brand new.
     */
    // @Test
    public void PostSignInView() throws Exception {
        // Set up the test
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        playerLobby.addPlayer(player);
        session.attribute("playerlobby", playerLobby);
        session.attribute("session_key", UIProtocol.PLAYERLOBBY_ATTR);

        // Invoke the test: a user signs in
        System.out.println("Invoking test...");
        CuT.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute(UIProtocol.PLAYER_ATTR, playerLobby.getPlayer(""));
        //   * test view name
        testHelper.assertViewName("home.ftl");

        //   * verify the session
        verify(session).attribute(eq(UIProtocol.PLAYERLOBBY_ATTR));

    }



}

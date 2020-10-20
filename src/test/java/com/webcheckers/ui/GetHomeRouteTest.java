package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);

        //unique CuT for each test meow if you see this
        gameCenter = new GameCenter();

        CuT = new GetHomeRoute(engine, gameCenter, playerLobby);
    }

    /**
     * Test that CuT shows the Home view when the session is brand new.
     */
   @Test
    public void PreSignIn() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute("message", GetHomeRoute.WELCOME_MSG);

        //There should be no player amount message because the current player isn't signed in yet
        testHelper.assertViewModelAttribute(UIProtocol.PLAYER_MSG_ATTR, null);

        testHelper.assertViewModelAttribute(UIProtocol.PLAYER_ATTR, playerLobby.getPlayer(""));
        //   * test view name
        testHelper.assertViewName("home.ftl");
        //   * verify the player lobby object
        verify(session).attribute(eq(UIProtocol.PLAYERLOBBY_ATTR), any(PlayerLobby.class));
    }


//    /**
//     * Test that CuT redirects to the Game view when a @Linkplain(PlayerServices) object exists.
//     */
//    @Test
//    public void postSignIn() {
//        final TemplateEngineTester testHelper = new TemplateEngineTester();
//        when(session.attribute(GetHomeRoute.PLAYERLOBBY_KEY)).thenReturn(gameCenter.newPlayerLobby());
//        // Invoke the test
//        try {
//            CuT.handle(request, response);
//            fail("Redirects invoke halt exceptions.");
//        } catch (HaltException e) {
//            // expected
//        }
//
//        // Analyze the results:
//        //   * redirect to the Game view
//        verify(response).redirect(WebServer.GAME_URL);
//    }
}

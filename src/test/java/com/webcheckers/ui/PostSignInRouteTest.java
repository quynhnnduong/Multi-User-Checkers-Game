package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

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

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session); // Shubhang what's this?
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);

        gameCenter = new GameCenter();

        CuT = new PostSignInRoute(engine, gameCenter, playerLobby);
    }

    /**
     * Test that CuT shows the SignIn view when the session is brand new.
     */
    @Test
    public void PreSignIn() throws Exception {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute("message", "Sign in with your username.");
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_MSG_ATTR, gameCenter.NO_PLAYERS);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_NAME_ATTR, playerLobby.getPlayer(""));

        //   * test view name
        testHelper.assertViewName("signIn.ftl");

        //   * verify the player lobby object
        verify(session).attribute(eq(GetHomeRoute.PLAYERLOBBY_KEY), any(PlayerLobby.class));
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

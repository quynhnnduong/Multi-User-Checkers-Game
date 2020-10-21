package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

import static com.webcheckers.ui.UIProtocol.*;
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
    private Session httpSession;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player currentUser;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        httpSession = mock(Session.class);
        when(request.session()).thenReturn(httpSession);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);

        //unique CuT for each test meow if you see this
        gameCenter = new GameCenter();
        currentUser = new Player("currentUser");

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
        verify(httpSession).attribute(eq(UIProtocol.PLAYERLOBBY_ATTR), any(PlayerLobby.class));
    }

    @Test
    public void postSignIn(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(httpSession.attribute(PLAYERLOBBY_ATTR)).thenReturn(playerLobby);
        when(httpSession.attribute(PLAYER_ATTR)).thenReturn(currentUser);
        when(httpSession.attribute(LEGIT_OPPONENT_ATTR)).thenReturn(true);
        currentUser.stopCalling();
        currentUser.exitGame();

        // Invoke the test
        CuT.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("currentUser", currentUser);
        testHelper.assertViewModelAttribute("playerList", playerLobby.getPlayers());
        testHelper.assertViewModelAttribute(PLAYER_MSG_ATTR, playerLobby.getLobbyMessage());
        testHelper.assertViewModelAttribute(LEGIT_OPPONENT_ATTR, true);

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

package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit Test suite for {@link GetSignOutRoute} component
 * @author Sasha Persaud
 */
@Tag("UI-tier")
public class GetSignOutRouteTest {
    private GetSignOutRoute CuT;
    private TemplateEngine engine;
    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;


    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);

        CuT = new GetSignOutRoute(engine, gameCenter, playerLobby);
    }

    @Test
    public void testLoadSignOut() throws Exception {
        // Set up
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke
        CuT.handle(request, response);

        // Analyze
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Sign Out");
        testHelper.assertViewModelAttribute("message", Message.info("Are you sure you want to sign out?"));
        testHelper.assertViewName("signout.ftl");
    }
}

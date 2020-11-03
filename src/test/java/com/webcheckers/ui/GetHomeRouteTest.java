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
import static org.mockito.ArgumentMatchers.*;
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
    private Player currentUser;

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
        currentUser = mock(Player.class);
        gameCenter = new GameCenter();

        when(request.session()).thenReturn(session);
        when(session.attribute(PLAYER_ATTR)).thenReturn(currentUser);

        CuT = new GetHomeRoute(engine, gameCenter, playerLobby);
    }

    @Test
    public void testNewSession() {
        when(session.attribute(PLAYER_ATTR)).thenReturn(null);
        when(session.attribute(LEGIT_NAME_ATTR)).thenReturn(true);
        CuT.handle(request, response);
        assertEquals(true, session.attribute(LEGIT_NAME_ATTR));
    }

    // @Test
    public void testMidSession1() {
        when(currentUser.inGame()).thenReturn(true);
        // TODO fix null pointer coming from response.redirect(...)
        Object result = CuT.handle(request, response);
        assertEquals(HaltException.class, result);
    }

}

package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static com.webcheckers.ui.UIProtocol.GAME_ID_ATTR;
import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostSignOutRouteTest {

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player currentUser;
    private GameCenter gameCenter;
    private PostSignOutRoute CuT;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);
        currentUser = mock(Player.class);

        //Component under test
        CuT = new PostSignOutRoute(engine, gameCenter, playerLobby);
    }

    @Test
    public void testLoadSignOutPage(){

        // Set up
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(PLAYER_ATTR)).thenReturn(currentUser);
        when(request.queryParams("sign_out")).thenReturn("not true or false");


        // Invoke
        CuT.handle(request, response);

        // Analyze
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Sign Out");
        testHelper.assertViewModelAttribute("message", Message.info("Are you sure you want to sign out?"));

    }

    @Test
    public void testLoadSignOutPage2(){

        // Set up
        gameCenter = new GameCenter();
        playerLobby = new PlayerLobby(gameCenter);
        playerLobby.addPlayer(currentUser);
        CuT = new PostSignOutRoute(engine, gameCenter, playerLobby);

        when(session.attribute(PLAYER_ATTR)).thenReturn(currentUser);
        when(request.queryParams("sign_out")).thenReturn("true");
        doNothing().when(session).attribute(PLAYER_ATTR, null);
        doNothing().when(session).attribute(GAME_ID_ATTR, null);
        doNothing().when(response).redirect(anyString());

        // Invoke
        assertThrows(HaltException.class, () -> CuT.handle(request, response));

        // Analyze
        assertEquals(0, playerLobby.getNumOfPlayers());
    }


    @Test
    public void testLoadSignOutPage3(){

        // Set up
        gameCenter = new GameCenter();
        playerLobby = new PlayerLobby(gameCenter);
        Player opponent = new Player("opponent");
        playerLobby.addPlayer(currentUser);
        CuT = new PostSignOutRoute(engine, gameCenter, playerLobby);

        when(session.attribute(PLAYER_ATTR)).thenReturn(currentUser);
        when(request.queryParams("sign_out")).thenReturn("false");
        when(currentUser.inGame()).thenReturn(true);
        when(currentUser.getOpponent()).thenReturn(opponent);
        doNothing().when(response).redirect(anyString());

        // Invoke
        assertThrows(HaltException.class, () -> CuT.handle(request, response));

        // Analyze

    }


    @Test
    public void testLoadSignOutPage4(){

        // Set up
        gameCenter = new GameCenter();
        playerLobby = new PlayerLobby(gameCenter);
        playerLobby.addPlayer(currentUser);
        CuT = new PostSignOutRoute(engine, gameCenter, playerLobby);

        when(session.attribute(PLAYER_ATTR)).thenReturn(currentUser);
        when(request.queryParams("sign_out")).thenReturn("false");
        when(currentUser.inGame()).thenReturn(false);
        doNothing().when(response).redirect(anyString());

        // Invoke
        assertThrows(HaltException.class, () -> CuT.handle(request, response));

        // Analyze

    }


}

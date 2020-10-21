package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static com.webcheckers.ui.UIProtocol.PLAYER_ATTR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        //friendlies
        playerLobby = new PlayerLobby(gameCenter);
        currentUser = new Player("User");

        //Component under test
        CuT = new PostSignOutRoute(engine, gameCenter, playerLobby);
    }

    @Test
    public void signOut() throws Exception {

        playerLobby.addPlayer(currentUser);
        when(session.attribute(PLAYER_ATTR)).thenReturn(currentUser);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        assertEquals(0, playerLobby.getNumOfPlayers(), "remove the signed in player");


        // Analyze the content passed into the render method
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Sign Out");


    }
}

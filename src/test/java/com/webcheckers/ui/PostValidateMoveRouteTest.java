package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostValidateMoveRouteTest {

    // Attributes holding mock objects
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;

    // Component under test (CuT)
    private PostValidateMoveRoute CuT;

    //friendlies
    private boolean isValidMove;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);

        CuT = new PostValidateMoveRoute(gameCenter);
    }

//    @Test
    public void moveIsValid() throws Exception {
        String messageText = "{\"sampleMove\": { \"row\": 1,\"col\": 1}}";
        when(request.queryParams("actionData")).thenReturn(messageText);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Object validMessage = CuT.handle(request, response);
        assertEquals(messageText, validMessage);

    }
}

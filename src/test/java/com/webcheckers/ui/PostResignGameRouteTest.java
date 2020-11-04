package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostResignGameRouteTest {

    private PostResignGame CuT;
    private TemplateEngine engine;
    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Player player;
    private Gson gson;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        // create friendlies
        gameCenter = new GameCenter();
        playerLobby = new PlayerLobby(gameCenter);
        player = new Player("Player");

        // create mocks
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        when(request.session()).thenReturn(session);
        when(session.attribute(UIProtocol.GAME_ID_ATTR)).thenReturn(null);
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player);


        gson = new Gson();
        CuT = new PostResignGame(gameCenter, playerLobby);
    }

    /**
     * Check if the resignation is successful
     */
    @Test
    public void resignGame() throws Exception {
        // Set up complete, Invoke
        Object resignation = CuT.handle(request, response);

        // Analyze
        assertEquals(gson.toJson(Message.info("Player resigned.")), resignation);
    }

}

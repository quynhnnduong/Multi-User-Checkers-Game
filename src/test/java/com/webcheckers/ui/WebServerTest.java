package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebServerTest {

    private TemplateEngine engine;
    private Gson gson;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private WebServer CuT;

    @BeforeEach
    public void setup(){
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);

        gson = new Gson();

        CuT = new WebServer(engine, gson, gameCenter, playerLobby);
    }

    @Test
    public void initApp(){
       CuT.initialize();
    }


}

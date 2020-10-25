package com.webcheckers.ui;


import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit Test suite for {@link GetGameRoute} component
 * @author Joel Clyne
 */
public class GetGameRouteTest {
    // Component under test (CuT)
    private GetGameRoute CuT;

    // Friendlies (already tested, no bugs will come from this)
    private GameCenter gameCenter;

    // Attributes holding mock objects
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player1;
    private Player player2;

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
        player1 = mock(Player.class);
        player2 = mock(Player.class);

        // create a unique CuT for each test
        CuT = new GetGameRoute(engine, gameCenter, playerLobby);
    }
    /**
     * Test that the Game view will create a new game when a player initiates a game
     */
    @Test
    public void new_game() {
        //create playerLobby and it's 2 players
        //gameCenter = new GameCenter();
        //playerLobby = new PlayerLobby(gameCenter);
        playerLobby.addPlayer(player1);
        player1.joinGame(true);
        playerLobby.addPlayer(player2);
        player2.joinGame(false);
        //Player player1 = new Player("Player1");


        //set up everything for player 1
        player1.exitGame();
        when(session.attribute(UIProtocol.PLAYER_ATTR)).thenReturn(player1);
        //when(session.attribute(RED_ATTR)).thenReturn(player1);

        //set up everything for player 2
        when(playerLobby.getPlayer(request.queryParams("opponent"))).thenReturn(player2);
        //when(session.attribute(WHITE_ATTR)).thenReturn(player2);

        //make the active color red
        //when(session.attribute(ACTIVE_COLOR_ATTR)).thenReturn(GetGameRoute.activeColor.RED);

        //make them play a game against each other
        playerLobby.setOpponentMatch(playerLobby.getPlayer("Player1"), playerLobby.getPlayer("Player2"));

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());


        // Invoke the test
        try {
            CuT.handle(request, response);
        } catch (Exception e){
            System.out.println("Route returned an exception during execution");
            e.printStackTrace();
        }

        // Analyze the content passed into the render method
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute("title", "Game");
        testHelper.assertViewModelAttribute("loggedIn", true);
        testHelper.assertViewModelAttribute("viewMode", GetGameRoute.viewMode.PLAY);
        //testHelper.assertViewModelAttribute("activeColor", GetGameRoute.activeColor.RED);

        //check if player 1 is red
        testHelper.assertViewModelAttribute("redPlayer", player1);
        //check if player 2 is white
        testHelper.assertViewModelAttribute("whitePlayer", player2);
    }




}

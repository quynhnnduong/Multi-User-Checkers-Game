package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit Test suite for {@link PlayerLobby} component
 * @author Sasha Persaud
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    // The component under test (CuT)
    private PlayerLobby CuT;

    // Friendly objects
    private GameCenter gameCenter;
    private Player player;

    @BeforeEach
    public void setup() {
        player = new Player("Player");
        gameCenter = new GameCenter();
        CuT = new PlayerLobby(gameCenter);
    }

    /**
     *Test the ability to make a new Game Center.
     */
    @Test
    public void testMakePlayerLobby(){
        assertNotNull(CuT);
    }

    /**
     *Test the ability to return the PlayerLobby.
     */
    @Test
    public void testGetPlayerLobby(){
        assert(CuT.getPlayers().isEmpty());
    }

    /**
     *Test the ability to add a player to the lobby.
     */
    @Test
    public void testAddPlayerToLobby(){
        CuT.addPlayer(player);
        assertNotNull(CuT.getPlayers());
    }

    @Test
    public void testAddPlayerToLobbyInvalid(){
        CuT.addPlayer(player);
        assertFalse(CuT.addPlayer(new Player("Player")));
    }

    //@Test
    public void testAddPlayerToLobbyInvalid2(){
        CuT.addPlayer(player);
        assertFalse(CuT.addPlayer(new Player("@@@")));
    }

    /**
     *Test the ability to retrieve a specific player from the lobby.
     */
    @Test
    public void testGetPlayerFromLobby(){
        CuT.addPlayer(player);
        assertEquals(player, CuT.getPlayer(player.getName()));
    }

    /**
     *Tests for the ability to retrieve the size of the Player Lobby.
     */
    // @Test
    public void testGetPlayerLobbySize0(){
        assertEquals(0, CuT.getNumOfPlayers());
    }

   // @Test
    public void testGetPlayerLobbySize1(){
        CuT.addPlayer(player);
        assertEquals(1, CuT.getNumOfPlayers());
    }

   // @Test
    public void testGetPlayerLobbySize3(){
        CuT.addPlayer(player);
        CuT.addPlayer(player);
        CuT.addPlayer(player);
        assertEquals(3, CuT.getNumOfPlayers());
    }

    /**
     *Test the ability to remove a player from the lobby.
     */
   @Test
    public void testRemovePlayerFromLobby(){
        // Setup
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        CuT.addPlayer(player1);
        CuT.addPlayer(player2);
        System.out.println(CuT.getNumOfPlayers());

        // Invoke
        CuT.removePlayer(player1);

        // Analyze
        assertEquals(1, CuT.getNumOfPlayers());
    }

    @Test
    public void testSetOpponentMatch(){
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        CuT.setOpponentMatch(player1, player2);
        assertEquals(player2, player1.getOpponent());
    }

    @Test
    public void testGetLobbyMessageWithPlayers(){
        // Setup
        Player player1 = new Player("Me");
        Player player2 = new Player("Player2");

        CuT.addPlayer(player1);
        CuT.addPlayer(player2);

        // Invoke
        String actualMessage = CuT.getLobbyMessage();

        // Analyze
        assertEquals("1 player(s) in lobby", actualMessage);
    }

    @Test
    public void testGetLobbyMessageNoPlayers(){
        // Invoke and Analyze with no one in the lobby
        assertEquals("There are no other players available to play at this time.", CuT.getLobbyMessage());
    }

}

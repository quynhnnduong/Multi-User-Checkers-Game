package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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

    // Attributes holding mock objects
    private Player player;

    @BeforeEach
    public void setup() {
        player = mock(Player.class);
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
    public void testGetPlayerLobbySet(){
        assert(CuT.getPlayerLobby().isEmpty());
    }

    /**
     *Test the ability to add a player to the lobby.
     */
    @Test
    public void testAddPlayerToLobby(){
        CuT.addPlayer("Player1");
        assert(!CuT.getPlayerLobby().isEmpty());
    }

    /**
     *Test the ability to retrieve a specific player from the lobby.
     */
    @Test
    public void testGetPlayerFromLobby(){
        CuT.addPlayer("Player1");
        assertEquals("Player1", CuT.getPlayer("Player1").getName());
    }

    /**
     *Tests for the ability to retrieve the size of the Player Lobby.
     */
    @Test
    public void testGetPlayerLobbySize0(){
        assertEquals(0, CuT.getPlayerSize());
    }

    @Test
    public void testGetPlayerLobbySize1(){
        CuT.addPlayer("Player1");
        assertEquals(1, CuT.getPlayerSize());
    }

    @Test
    public void testGetPlayerLobbySize3(){
        CuT.addPlayer("Player1");
        CuT.addPlayer("Player2");
        CuT.addPlayer("Player3");
        assertEquals(3, CuT.getPlayerSize());
    }

    /**
     *Test
     */
    @Test
    public void testRemovePlayerFromLobby(){}

    /**
     *Test
     */
    @Test
    public void testGetPlayerLobby(){}

    /**
     *Test
     */
    @Test
    public void testStartPlayer(){}

    /**
     *Test
     */
    @Test
    public void testStopPlayer(){}

    /**
     *Test
     */
    @Test
    public void testSetOpponentMatch(){}

    /**
     *Test
     */
    @Test
    public void testGetPlayerOpponent(){}

    /**
     *Test
     */
    @Test
    public void testIsNameValid(){}

}

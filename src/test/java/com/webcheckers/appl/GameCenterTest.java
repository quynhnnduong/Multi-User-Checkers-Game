package com.webcheckers.appl;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit Test suite for {@link GameCenter} component
 * @author Shubhang Mehrotra
 */
@Tag("Application-tier")
public class GameCenterTest {
    /**
     *Test the ability to make a new PlayerLobby.
     */
    @Test
    public void testMakePlayerLobby(){
        final GameCenter CuT = new GameCenter();
        //invoke test

        final PlayerLobby playerLobby = CuT.newPlayerLobby();
        //Analyze results
        assertNotNull(playerLobby);
    }

    /**
     * Test no player in player lobby message
     */
    @Test
    public void testGetPlayersMessage_0(){
        final GameCenter CuT = new GameCenter();
        assertEquals(CuT.getPlayersMessage(), CuT.NO_PLAYERS);
    }

    /**
     * Test one player in player lobby message
     */
    @Test
    public void testGetPlayersMessage_1(){
        final GameCenter CuT = new GameCenter();

        CuT.addPlayerToLobby(); //simulate one player being added to lobby

        assertEquals(CuT.getPlayersMessage(), String.format(CuT.PLAYERS_ONLINE, 1));
    }

    /**
     * Test two players in player lobby message
     */
    @Test
    public void testGetPlayersMessage_2(){
        final GameCenter CuT = new GameCenter();

        CuT.addPlayerToLobby(); //simulate two players being added to lobby
        CuT.addPlayerToLobby();

        assertEquals(CuT.getPlayersMessage(), String.format(CuT.PLAYERS_ONLINE, 2));
    }

    /**
     * Test three player in player lobby message
     */
    @Test
    public void testGetPlayersMessage_3(){
        final GameCenter CuT = new GameCenter();

        CuT.addPlayerToLobby(); //simulate three players being added to lobby
        CuT.addPlayerToLobby();
        CuT.addPlayerToLobby();

        assertEquals(CuT.getPlayersMessage(), String.format(CuT.PLAYERS_ONLINE, 3));
    }

}

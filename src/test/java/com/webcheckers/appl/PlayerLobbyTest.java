package com.webcheckers.appl;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test suite for {@link PlayerLobby} component
 * @author Shubhang Mehrotra
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    /**
     *Test the ability to make a new Game Center.
     */
    @Test
    public void testMakePlayerLobby(){
        final GameCenter gameCenter = new GameCenter();
        final PlayerLobby CuT = new PlayerLobby(gameCenter);
        //Analyze results
        assertNotNull(CuT);
    }


}

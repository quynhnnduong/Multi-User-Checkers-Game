package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for {@link Player} Component
 *
 * @author Dmitry Selin
 */
@Tag("Model-tier")
public class PlayerTest {

    /** A placeholder string for creating a testable instance of Player */
    private final String NAME = "TestName";

    /** Tests the constructor of Player */
    @Test
    public void player_constructor() {
        final Player player = new Player(NAME);

        // Ensures that the instance variables have been initialized correctly
        assertEquals(NAME, player.getName());
        assertTrue(player.getIsSignedIn());
        assertFalse(player.getIsMidGame());
        assertFalse(player.getCallingPlayer());
    }

    /** Tests the getName() method of Player */
    @Test
    public void name_not_null() {
        final Player player = new Player(NAME);

        assertNotNull(player.getName());
    }

    /** Tests the signIn() method of Player */
    @Test
    public void sign_in() {
        final Player player = new Player(NAME);
        player.signOut();

        assertFalse(player.getIsSignedIn());
        player.signIn();
        assertTrue(player.getIsSignedIn());
    }

    /** Tests the signOut() method of Player */
    @Test
    public void sign_out() {
        final Player player = new Player(NAME);

        player.signOut();
        assertFalse(player.getIsSignedIn());
    }

    /** Tests the startPlaying() method of Player */
    @Test
    public void start_playing() {
        final Player player = new Player(NAME);

        player.startPlaying();
        assertTrue(player.getIsMidGame());
    }

    /** Tests the stopPlaying() method of Player */
    @Test
    public void stop_playing() {
        final Player player = new Player(NAME);

        player.startPlaying();
        player.stopPlaying();
        assertFalse(player.getIsMidGame());
    }

    /** Tests the startCallingPlayer() method of Player */
    @Test
    public void start_calling_player() {
        final Player player = new Player(NAME);

        player.startCallingPlayer();
        assertTrue(player.getCallingPlayer());
    }

    /** Tests the stopCallingPlayer() method of Player */
    @Test
    public void stop_calling_player() {
        final Player player = new Player(NAME);

        player.startCallingPlayer();
        player.stopCallingPlayer();
        assertFalse(player.getCallingPlayer());
    }

    /** Tests the setOpponent() and getOpponent() methods of Player */
    @Test
    public void set_opponent() {
        final Player player = new Player(NAME);
        assertNull(player.getOpponent());

        player.setOpponent(new Player(NAME));
        assertNotNull(player.getOpponent());
    }
}

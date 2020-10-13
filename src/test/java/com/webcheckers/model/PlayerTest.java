package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
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

    /** Component under test (Player Class) */
    private Player CuT;

    /** A placeholder string for creating a testable instance of Player */
    private final String NAME = "TestName";

    /** This is run before every test to create a fresh CuT object for each test */
    @BeforeEach
    public void setup() { CuT = new Player(NAME); }

    /** Tests the constructor of Player */
    @Test
    public void player_constructor() {

        // Ensures that the instance variables have been initialized correctly
        assertEquals(NAME, CuT.getName());
        assertTrue(CuT.getIsSignedIn());
        assertFalse(CuT.getIsMidGame());
        assertFalse(CuT.getCallingPlayer());
    }

    /** Tests the getName() method of Player */
    @Test
    public void name_not_null() { assertNotNull(CuT.getName()); }

    /** Tests the signIn() method of Player */
    @Test
    public void sign_in() {
        CuT.signOut();

        assertFalse(CuT.getIsSignedIn());
        CuT.signIn();
        assertTrue(CuT.getIsSignedIn());
    }

    /** Tests the signOut() method of Player */
    @Test
    public void sign_out() {
        CuT.signOut();
        assertFalse(CuT.getIsSignedIn());
    }

    /** Tests the startPlaying() method of Player */
    @Test
    public void start_playing() {
        CuT.startPlaying();
        assertTrue(CuT.getIsMidGame());
    }

    /** Tests the stopPlaying() method of Player */
    @Test
    public void stop_playing() {
        CuT.startPlaying();
        CuT.stopPlaying();
        assertFalse(CuT.getIsMidGame());
    }

    /** Tests the startCallingPlayer() method of Player */
    @Test
    public void start_calling_player() {
        CuT.startCallingPlayer();
        assertTrue(CuT.getCallingPlayer());
    }

    /** Tests the stopCallingPlayer() method of Player */
    @Test
    public void stop_calling_player() {
        CuT.startCallingPlayer();
        CuT.stopCallingPlayer();
        assertFalse(CuT.getCallingPlayer());
    }

    /** Tests the setOpponent() and getOpponent() methods of Player */
    @Test
    public void set_opponent() {
        assertNull(CuT.getOpponent());

        CuT.setOpponent(new Player(NAME));
        assertNotNull(CuT.getOpponent());
    }
}

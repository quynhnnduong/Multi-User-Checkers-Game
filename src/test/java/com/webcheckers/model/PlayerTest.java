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
        assertFalse(CuT.isCalledForGame());
    }

    /** Tests the getName() method of Player */
    @Test
    public void name_not_null() { assertNotNull(CuT.getName()); }

    /** Tests the isCalledForGame() method of Player */
    @Test
    public void start_calling_player() {
        CuT.call();
        assertTrue(CuT.isCalledForGame());
    }

    /** Tests the setOpponent() and getOpponent() methods of Player */
    @Test
    public void set_opponent() {
        assertNull(CuT.getOpponent());

        CuT.setOpponent(new Player(NAME));
        assertNotNull(CuT.getOpponent());
    }

    /** Tests inGame() method of Player */
    @Test
    public void player_in_game(){
        CuT.call();
        CuT.isMyTurn();
        CuT.endTurn();
        assertTrue(CuT.inGame());
    }

    /** Tests isMyTurn() method of Player*/
    @Test
    public void player_start_turn(){
        CuT.startTurn();
        assertTrue(CuT.isMyTurn());
    }

    @Test
    public void player_Signed_In(){
        CuT.signIn();
        assertTrue(CuT.isSignedIn());
    }

    @Test
    public void player_join_Game(){
        CuT.startTurn();
        CuT.joinGame(true);
        assertTrue(CuT.inGame());

    }

    @Test
    public void player_exit_Game(){
        CuT.exitGame();
        assertNull(CuT.getOpponent());
    }
}

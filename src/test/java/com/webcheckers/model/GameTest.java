package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for {@link Game} Component
 * @author Sasha Persaud, Shubhang Mehrotra
 */
@Tag("Model-tier")
public class GameTest {

    // Component under test
    private Game CuT;

    // Friendlies
    private Player redPlayer;
    private Player whitePlayer;
    private String id;

    @BeforeEach
    private void setUp(){
        redPlayer = new Player("RedPlayer");
        whitePlayer = new Player("WhitePlayer");
        id = "1234";
    }

    @Test
    public void testCreateGame(){
        // Invoke
        CuT = new Game(id,redPlayer, whitePlayer);

        // Analyze
        assertNotNull(CuT);
    }

    @Test
    public void testGetRedPlayer(){
        // Invoke
        CuT = new Game(id,redPlayer, whitePlayer);
        Player testPlayer = CuT.getRedPlayer();

        // Analyze
        assertEquals(redPlayer, testPlayer);
    }

   @Test
    public void testGetWhitePlayer(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        Player testPlayer = CuT.getWhitePlayer();

        // Analyze
        assertEquals(whitePlayer, testPlayer);
    }

    @Test
    public void testGetId(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        String testID = CuT.getId();

        // Analyze
        assertEquals(id, testID);
    }
}

package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for {@link Game} Component
 * @author Sasha Persaud, Shubhang Mehrotra, Quynh Duong
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
    public void testGetRedView(){
        // Invoke
        CuT = new Game(id,redPlayer, whitePlayer);
        BoardView redView = CuT.getRedView();

        // Analyze
        assertEquals(redPlayer, redView);
    }

    @Test
    public void testGetId(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        String testID = CuT.getId();

        // Analyze
        assertEquals(id, testID);
    }

    @Test
    public void testGameEnded(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        redPlayer.exitGame();
        whitePlayer.exitGame();

        //analyze
        assertTrue(CuT.hasGameEnded());

    }
    @Test
    public void testGameNotEnded(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        redPlayer.startTurn();
        whitePlayer.endTurn();

        //analyze
        assertFalse(CuT.hasGameEnded());

    }
    @Test
    public void testGameNotEnded2(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        redPlayer.startTurn();
        whitePlayer.exitGame();

        //analyze
        assertFalse(CuT.hasGameEnded());

    }

    @Test
    public void testGameNotEnded3(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        redPlayer.exitGame();
        whitePlayer.startTurn();

        //analyze
        assertFalse(CuT.hasGameEnded());

    }

    @Test
    public void testHasPlayerResigned(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        redPlayer.exitGame();
        whitePlayer.exitGame();

        //analyze
        assertTrue(CuT.hasPlayerResigned());
    }

    @Test
    public void testHasPlayerResigned2(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        redPlayer.exitGame();
        whitePlayer.startTurn();

        //analyze
        assertTrue(CuT.hasPlayerResigned());
    }

    @Test
    public void testHasPlayerResigned3(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        redPlayer.startTurn();
        whitePlayer.exitGame();

        //analyze
        assertTrue(CuT.hasPlayerResigned());
    }

    @Test
    public void testPlayerNotResigned(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        redPlayer.startTurn();
        whitePlayer.endTurn();

        //analyze
        assertFalse(CuT.hasPlayerResigned());
    }

    @Test
    public void testEndTurn(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        Move move = CuT.getCurrentTurn().getFirstMove();

    }
}

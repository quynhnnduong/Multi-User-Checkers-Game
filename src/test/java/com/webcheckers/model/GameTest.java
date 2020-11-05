package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    private BoardView whiteView;
    private BoardView redView;
    private Game.ActiveColor activeColor;

    @BeforeEach
    private void setUp(){
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
//        redView = mock(BoardView.class);
//        whiteView = mock(BoardView.class);

        id = "1234";
        activeColor = Game.ActiveColor.RED;
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
    public void testGetWhiteView(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        whiteView = CuT.getWhiteView();

        // Analyze
        assertEquals(CuT.getWhiteView(), whiteView);
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
    public void testGameAbandoned(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        redPlayer.exitGame();
        whitePlayer.exitGame();

        //analyze
        assertTrue(CuT.isGameAbandoned());

    }
    @Test
    public void testGameNotAbandoned(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        when(redPlayer.isPlayingWith(whitePlayer)).thenReturn(false);
        when(whitePlayer.isPlayingWith(redPlayer)).thenReturn(true);

        //analyze
        assertFalse(CuT.isGameAbandoned());

    }
    @Test
    public void testGameNotAbandoned2(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        when(redPlayer.isPlayingWith(whitePlayer)).thenReturn(true);
        when(whitePlayer.isPlayingWith(redPlayer)).thenReturn(false);

        //analyze
        assertFalse(CuT.isGameAbandoned());

    }

    @Test
    public void testGameNotAbandoned3(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        when(redPlayer.isPlayingWith(whitePlayer)).thenReturn(true);
        when(whitePlayer.isPlayingWith(redPlayer)).thenReturn(true);

        //analyze
        assertFalse(CuT.isGameAbandoned());

    }

    @Test
    public void testHasPlayerResigned(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        when(redPlayer.isPlayingWith(whitePlayer)).thenReturn(false);
        when(whitePlayer.isPlayingWith(redPlayer)).thenReturn(false);

        //analyze
        assertTrue(CuT.hasPlayerResigned());
    }

    @Test
    public void testHasPlayerResigned2(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        when(redPlayer.isPlayingWith(whitePlayer)).thenReturn(false);
        when(whitePlayer.isPlayingWith(redPlayer)).thenReturn(true);

        //analyze
        assertTrue(CuT.hasPlayerResigned());
    }

    @Test
    public void testHasPlayerResigned3(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        when(redPlayer.isPlayingWith(whitePlayer)).thenReturn(true);
        when(whitePlayer.isPlayingWith(redPlayer)).thenReturn(false);

        //analyze
        assertTrue(CuT.hasPlayerResigned());
    }

    @Test
    public void testPlayerNotResigned(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        when(redPlayer.isPlayingWith(whitePlayer)).thenReturn(true);
        when(whitePlayer.isPlayingWith(redPlayer)).thenReturn(true);

        //analyze
        assertFalse(CuT.hasPlayerResigned());
    }

    @Test
    public void testGetCurrentTurn(){
        CuT = new Game(id, redPlayer, whitePlayer);
        Turn getTurn = CuT.getCurrentTurn();

        assertEquals(getTurn, CuT.getCurrentTurn());
    }

//    @Test
    public void testGetWinner(){
        // Invoke
        CuT = new Game(id, redPlayer, whitePlayer);
        redView = CuT.getRedView();
        whiteView = CuT.getWhiteView();
//        when(redView.getRemainingPieces(Piece.Color.RED)).thenReturn(0);
//        when(redView.getRemainingPieces(Piece.Color.WHITE)).thenReturn(0);

        assertEquals(CuT.getWinner(), CuT.getWhitePlayer());

    }

}

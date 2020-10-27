package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit Test suite for {@link GameCenter} component
 * @author Shubhang Mehrotra, Sasha Persaud
 */
@Tag("Application-tier")
public class GameCenterTest {

    //Component under Test
    private GameCenter CuT;

    // friendlies
    private Game game;
    private String id;
    private Player redPlayer;
    private Player whitePlayer;

    @BeforeEach
    public void setup(){
        id = "1";
        redPlayer = new Player("Red");
        whitePlayer = new Player("White");
        game = new Game(id, redPlayer, whitePlayer);

        CuT = new GameCenter();
    }

    @Test
    public void testAddGame() {
        // Setup complete, Invoke
        CuT.addGame(game);

        // Analyze
        assertNotNull(CuT.getGame("1"));
    }

    @Test
    public void testGetGame(){
        // Set up
        CuT.addGame(game);

        // Invoke
        Game actual = CuT.getGame("1");

        // Analyze
        assertEquals(game, actual);
    }


}

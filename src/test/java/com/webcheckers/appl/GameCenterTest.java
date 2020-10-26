package com.webcheckers.appl;

import com.webcheckers.model.Game;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit Test suite for {@link GameCenter} component
 * @author Shubhang Mehrotra
 */
@Tag("Application-tier")
public class GameCenterTest {

    //mocks
    private PlayerLobby playerLobby;
    private Game game;

    //Class under Test
    private GameCenter CuT;

    private HashMap<String, Game> games;

    @Test
    public void setup(){
        this.game = mock(Game.class);
        CuT = new GameCenter();
    }

    @Test
    public void testAddGame(Game game) {games.put(game.getId(), game);}

    @Test
    public Game testGetGame(String id){ return games.get(id);}


}

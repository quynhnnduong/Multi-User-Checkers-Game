package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Tag("Model-tier")
public class ReplayTest {

    // Component under Test
    private Replay CuT;

    // Friendlies
    private Game game;
    private Player redPlayer;
    private Player whitePlayer;
    private String gameId;
    private Move move;
    private BoardView board;
    private BoardView fakewhite;
    private ArrayList<ReplayMove> turnsList;

    @BeforeEach
    public void setUp() {
        gameId = "1234";
        turnsList = mock(ArrayList.class);
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        game = mock(Game.class);
        move = mock(Move.class);
        board = mock(BoardView.class);
        fakewhite = mock(BoardView.class);

        CuT = new Replay(gameId, turnsList, game);
    }

    @Test
    public void testGetGameId(){
        assertEquals(CuT.getGameId(), gameId);
    }

    @Test
    public void testIncrementTurn(){
        when(turnsList.size()).thenReturn(1);
        CuT = new Replay(gameId, turnsList, game);

    }

    @Test
    public void testNotIncrementTurn(){
        when(turnsList.size()).thenReturn(0);
        CuT = new Replay(gameId, turnsList, game);

    }

    @Test
    public void testDecrementTurn(){
        when(turnsList.size()).thenReturn(1);
        CuT = new Replay(gameId, turnsList, game);

        CuT.incrementTurn();
    }

    @Test
    public void testNotDecrementTurn(){
        when(turnsList.size()).thenReturn(0);
        CuT = new Replay(gameId, turnsList, game);

        CuT.incrementTurn();
    }

//    @Test
    public void testExecuteReplayMove(){
        CuT.executeReplayMove(Game.ActiveColor.RED, move, board, fakewhite);
        CuT = new Replay(gameId, turnsList, game);
        doNothing().when(board).makeMoveReplayVer(move);
        doNothing().when(fakewhite).makeMoveReplayVer(move.getFlippedMove());
    }

    @Test
    public void testGetPlayers(){
        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        CuT = new Replay(gameId, turnsList, game);
        assertEquals(CuT.getRed(), redPlayer);
        assertEquals(CuT.getWhite(), whitePlayer);

    }

    @Test
    public void testGetGame(){
        CuT = new Replay(gameId, turnsList, game);

        assertEquals(CuT.getGame(), game);
    }

    @Test
    public void testGetMaxTurns(){
        CuT = new Replay(gameId, turnsList, game);

        assertEquals(CuT.getMaxTurns(), -1);
    }

}

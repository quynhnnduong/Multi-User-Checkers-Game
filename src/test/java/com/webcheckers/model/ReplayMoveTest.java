package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Model-tier")
public class ReplayMoveTest {

    // Component under Test
    private ReplayMove CuT;

    // Friendlies
    private Game.ActiveColor playerColor;
    private Move move;
    private Position start, end;

    @BeforeEach
    public void setUp() {
        playerColor = Game.ActiveColor.RED;
        start = new Position(0, 0);
        end = new Position(1, 1);
        move = new Move(start,end);
        CuT = new ReplayMove(playerColor, move);
    }

    @Test
    public void testGetPlayerColor() {
        // Invoke
        Game.ActiveColor result = CuT.getPlayerColor();

        // Analyze
        assertEquals(Game.ActiveColor.RED, result);
    }

    @Test
    public void testGetMove() {
        // Invoke
        Move result = CuT.getMove();

        // Analyze
        assertEquals(move, result);
    }
}

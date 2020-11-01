package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for {@link Turn} Component
 *
 * @author Shubhang Mehrotra
 */
@Tag("Model-tier")
public class TurnTest {

    //CuT
    private Turn CuT = new Turn();

    //
    private Move move;

    @BeforeEach
    public void setup() {
        move = mock(Move.class);
        ArrayList<Move> validMoves = new ArrayList<>();
    }
    @Test
    public void testAddValidMove(){

        CuT.addValidMove(move);

        assertTrue(CuT.hasMoves());
    }


}

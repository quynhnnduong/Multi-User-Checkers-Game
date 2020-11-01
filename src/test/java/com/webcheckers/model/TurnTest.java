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
    private Turn CuT;

    //friendlies
    private ArrayList<Move> validMoves;

    //mocks
    private Move move;

    @BeforeEach
    public void setup() {
        CuT = new Turn();
        move = mock(Move.class);
        validMoves = new ArrayList<>();
    }

    @Test
    public void testHasMoves(){
        CuT.addValidMove(move);
        assertTrue(CuT.hasMoves());
    }

    @Test
    public void testHasMovesFail(){
        assertFalse(CuT.hasMoves());
    }

    @Test
    public void testAddValidMove(){
        CuT.addValidMove(move);
        assertTrue(CuT.hasMoves());
    }

    @Test
    public void testRemoveLastValidMove(){
        //add moves to the list
        CuT.addValidMove(move);
        CuT.addValidMove(move);

        assertTrue(CuT.hasMoves());

        CuT.removeLastValidMove();

        assertEquals(1, CuT.validMoves.size());
    }

    @Test
    public void testRemoveLastValidMoveFail(){
        CuT.removeLastValidMove();
        assertEquals(0,CuT.validMoves.size());
    }


    @Test
    public void testGetFirstMove(){
        CuT.addValidMove(move);
        CuT.addValidMove(move);
        assertTrue(CuT.hasMoves());

        Move firstMove = CuT.getFirstMove();

       assertEquals(move, firstMove);
    }

    @Test
    public void testGetFirstMoveFail(){
        assertFalse(CuT.hasMoves());
        Move firstMove =  CuT.getFirstMove();
        assertNull(firstMove);
    }


}

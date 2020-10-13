package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Unit Tests for {@link Piece} Component
 * @author Quynh Duong
 */
@Tag("Model-tier")
public class PieceTest {

    //private enum type {SINGLE, KING}
    //private enum type {RED, WHITE}
    private Piece.Type single = Piece.Type.SINGLE;
    private Piece.Type king = Piece.Type.KING;
    private Piece.Color red = Piece.Color.RED;
    private Piece.Color white = Piece.Color.WHITE;

    public Piece singleRed = new Piece(red);
    public Piece kingWhite = new Piece(white);


    /*
     * Test that the Piece constructor works
     */
    @Test
    public void createPiece(){
        assertSame(singleRed.getType(), single);
        assertSame(singleRed.getColor(), red);
    }

}

package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for {@link Piece} Component
 * @author Quynh Duong
 */
@Tag("Model-tier")
public class PieceTest {

    /** The color of the checkers Piece */
    private final Piece.Color redPiece = Piece.Color.RED;
    private final Piece.Color whitePiece = Piece.Color.WHITE;


    /** The type of checkers Piece this object is */
    private Piece.Type singleType = Piece.Type.SINGLE;

    public Piece singleRed = new Piece(redPiece);
    public Piece singleWhite = new Piece(whitePiece);


    /** Tests the constructor of Space */
    @Test
    public void piece_constructor(){
        // Ensures that the instance variables are initialized correctly
        assertSame(singleRed.getType(), singleType);
        assertSame(singleRed.getColor(), redPiece);
    }

    /** Tests the getColor() method of Piece */
    @Test
    public void getColorPiece() {

        // Ensures that the instance variables are initialized correctly
        assertEquals(redPiece, singleRed.getColor());
        assertEquals(whitePiece, singleWhite.getColor());
    }

    /** Tests the getType() method of Piece */
    @Test
    public void getTypePiece(){
        assertEquals(singleType, singleRed.getType());
    }


}

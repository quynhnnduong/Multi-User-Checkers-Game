package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for {@link Space} Component
 *
 * @author Dmitry Selin
 */
@Tag("Model-tier")
public class SpaceTest {

    /** A testable int value for the cell index of a Space - used for object creation */
    private final int CELL_INDEX = 0;

    /** A testable SpaceColor representing the color of a Space */
    private final SpaceColor COLOR = SpaceColor.WHITE;

    /** A standard Piece object used for testing the Space class */
    private final Piece PIECE = new Piece(Piece.Color.RED);

    /** Tests the constructor of Space */
    @Test
    public void space_constructor() {
        final Space space = new Space(CELL_INDEX, COLOR);

        // Ensures that the instance variables are initialized correctly
        assertEquals(CELL_INDEX, space.getCellIdx());
        assertEquals(COLOR, space.getColor());
        assertNull(space.getPiece());
    }

    /** Tests the placePiece() method of Space */
    @Test
    public void place_piece() {
        final Space space = new Space(CELL_INDEX, COLOR);

        space.placePiece(PIECE);
        assertNotNull(space.getPiece());
    }

    /** Tests the getPiece() method of Space */
    @Test
    public void get_piece() {
        final Space space = new Space(CELL_INDEX, COLOR);

        space.placePiece(PIECE);
        assertEquals(PIECE, space.getPiece());
    }

    /** Tests the isValid() method of Space with a black Space */
    @Test
    public void is_black_valid() {
        final Space space = new Space(CELL_INDEX, SpaceColor.BLACK);

        // A Space is only valid when it is black in color and has no Piece on it
        assertTrue(space.isValid());

        space.placePiece(PIECE);
        assertFalse(space.isValid());
    }

    /** Tests the isValid() method of Space with a white Space */
    @Test
    public void is_white_valid() {
        final Space space = new Space(CELL_INDEX, SpaceColor.WHITE);

        // All cases here must be invalid since the Space is not black
        assertFalse(space.isValid());
        space.placePiece(PIECE);
        assertFalse(space.isValid());
    }
}

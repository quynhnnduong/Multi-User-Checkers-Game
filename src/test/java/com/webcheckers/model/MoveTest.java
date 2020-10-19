package com.webcheckers.model;

import org.junit.jupiter.api.Tag;

import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for {@link Piece} Component
 * @author Sasha Persaud
 */
@Tag("Model-tier")
public class MoveTest {

    // Component under test
    private Move CuT;

    // attributes for mock objects
    private Position start = mock(Position.class);
    private Position end = mock(Position.class);

    @Test
    public void testMoveConstructor() {
        // Set up is complete. Invoke test
        CuT = new Move(start, end);

        // Analyze results
        assertNotNull(CuT);
    }

    @Test
    public void testGetStart() {
        // Setup/Invoke
        CuT = new Move(start, end);

        // Analyze
        assertEquals(start, CuT.getStart());
    }

    @Test
    public void testGetEnd() {
        // Setup/Invoke
        CuT = new Move(start, end);

        // Analyze
        assertEquals(end, CuT.getEnd());
    }
}

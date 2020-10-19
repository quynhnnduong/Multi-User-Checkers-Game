package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for {@link Position} Component
 * @author Sasha Persaud
 */
@Tag("Model-tier")
public class PositionTest {

    // Component under test
    private Position CuT;

    // Friendlies
    private int row;
    private int cell;

    @BeforeEach
    public void setUp(){
        row = 1;
        cell = 2;
    }

    @Test
    public void testCreatePosition(){
        // Invoke
        CuT = new Position(row, cell);

        // Analyze
        assertNotNull(CuT);
    }

    @Test
    public void testGetCell(){
        // Invoke
        CuT = new Position(row, cell);

        // Analyze
        assertEquals(cell, CuT.getCell());
    }

    @Test
    public void getRow(){
        // Invoke
        CuT = new Position(row, cell);

        // Analyze
        assertEquals(row, CuT.getRow());
    }

}
